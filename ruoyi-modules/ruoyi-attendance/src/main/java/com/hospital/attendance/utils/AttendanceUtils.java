package com.hospital.attendance.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.hospital.attendance.constant.AttendanceCacheNames;
import com.hospital.attendance.domain.*;
import com.hospital.attendance.domain.vo.*;
import com.hospital.attendance.enums.AttendanceKindEnum;
import com.hospital.attendance.enums.AttendanceStatusEnum;
import com.hospital.attendance.mapper.*;
import org.dromara.common.core.enums.YesNoEnum;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.core.utils.system.UserUtils;
import org.dromara.common.redis.utils.RedisUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 考勤工具类
 *
 * @author liguoxian
 */
public class AttendanceUtils {

    private static final Map<Long, Set<String>> scheduleIds = new HashMap<>();
    private static final AttendanceClassesMapper classesMapper = SpringUtils.getBean(AttendanceClassesMapper.class);
    private static final AttendanceGroupMapper groupMapper = SpringUtils.getBean(AttendanceGroupMapper.class);
    private static final AttendanceGroupClassesMapper groupClassesMapper = SpringUtils.getBean(AttendanceGroupClassesMapper.class);
    private static final AttendanceGroupAreaMapper groupAreaMapper = SpringUtils.getBean(AttendanceGroupAreaMapper.class);
    private static final AttendanceGroupUserMapper groupUserMapper = SpringUtils.getBean(AttendanceGroupUserMapper.class);
    private static final AttendanceFlowMapper attendanceFlowMapper = SpringUtils.getBean(AttendanceFlowMapper.class);

    /**
     * 根据用户ID获取对应的考勤组信息集合
     *
     * @param userId 用户ID
     * @return 用户所在的考勤组
     */
    public static List<AttendanceGroup> getMyGroup(Long userId) {
        List<Long> groupIds = getMyGroupId(userId);
        if (CollUtil.isNotEmpty(groupIds)) {
            return groupIds.stream()
                    .distinct()
                    .map(AttendanceUtils::getGroup)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 根据用户ID获取对应的考勤组ID集合
     *
     * @param userId 用户ID
     * @return 用户所在的考勤组
     */
    public static List<Long> getMyGroupId(Long userId) {
        String userKey = getKey(CacheTypeEnum.users, "users");
        Map<String, List<Long>> userMap = RedisUtils.getCacheObject(userKey);
        if (userMap == null) {
            initUserGroupCache();
            userMap = RedisUtils.getCacheObject(userKey);
        }
        return userMap.get(userId.toString());
    }

    /**
     * 根据考勤组ID获取对应的考勤用户ID集合
     *
     * @param groupId 考勤组ID
     * @return 在该考勤组的用户
     */
    public static List<Long> getUsersByGroupId(Long groupId) {
        String groupKey = getKey(CacheTypeEnum.users, "groups");
        Map<String, List<Long>> groupMap = RedisUtils.getCacheObject(groupKey);
        if (groupMap == null) {
            initUserGroupCache();
            groupMap = RedisUtils.getCacheObject(groupKey);
        }
        return groupMap.get(groupId.toString());
    }

    /**
     * 清除用户考勤信息缓存
     *
     * @return 成功状态
     */
    public static boolean removeGroupUserCache() {
        String userKey = getKey(CacheTypeEnum.users, "users");
        String groupKey = getKey(CacheTypeEnum.users, "groups");
        return RedisUtils.deleteObject(userKey) && RedisUtils.deleteObject(groupKey);
    }

    /**
     * 更新用户考勤组缓存
     */
    public static void initUserGroupCache() {
        String userKey = getKey(CacheTypeEnum.users, "users");
        String groupKey = getKey(CacheTypeEnum.users, "groups");
        List<AttendanceGroupUser> groupUsers = groupUserMapper.selectList();
        Map<String, List<Long>> userMap = groupUsers.stream()
                .collect(Collectors.groupingBy(obj -> obj.getUserId().toString(), Collectors.mapping(AttendanceGroupUser::getGroupId, Collectors.toList())));

        Map<String, List<Long>> groupMap = groupUsers.stream()
                .collect(Collectors.groupingBy(obj -> obj.getGroupId().toString(), Collectors.mapping(AttendanceGroupUser::getUserId, Collectors.toList())));
        removeGroupUserCache();
        RedisUtils.setCacheObject(userKey, userMap);
        RedisUtils.setCacheObject(groupKey, groupMap);
    }

    /**
     * 根据ID获取考勤组信息
     *
     * @param groupId 考勤组ID
     * @return 考勤组信息
     */
    public static AttendanceGroup getGroup(Long groupId) {
        String key = getKey(CacheTypeEnum.group, groupId);
        AttendanceGroup group = RedisUtils.getCacheObject(key);
        if (Objects.isNull(group)) {
            initGroupCache(groupId);
            group = RedisUtils.getCacheObject(key);
        }
        return group;
    }

    /**
     * 清除考勤组缓存
     */
    public static void removeGroupCache() {
        RedisUtils.deleteKeys(getKey(CacheTypeEnum.group, "*"));
    }


    /**
     * 根据ID清除考勤组缓存
     *
     * @param groupId 考勤组ID
     * @return 成功状态
     */
    public static boolean removeGroupCache(Long groupId) {
        return RedisUtils.deleteObject(getKey(CacheTypeEnum.group, groupId));
    }

    /**
     * 更新所有考勤组缓存
     */
    public static void initGroupCache() {
        List<AttendanceGroup> attendanceGroups = groupMapper.selectList();
        removeGroupCache();
        attendanceGroups.parallelStream().forEach(group -> {
            String key = getKey(CacheTypeEnum.group, group.getId());
            RedisUtils.setCacheObject(key, group);
        });
    }

    /**
     * 根据考勤组ID更新考勤组缓存
     *
     * @param groupId 考勤组ID
     */
    public static void initGroupCache(Long groupId) {
        AttendanceGroup group = groupMapper.selectById(groupId);
        removeGroupCache(groupId);
        if (Objects.nonNull(group)) {
            String key = getKey(CacheTypeEnum.group, group.getId());
            RedisUtils.setCacheObject(key, group);
        }
    }

    /**
     * 根据考勤组ID获取当日的考勤班次信息
     *
     * @param groupId 考勤组ID
     * @return 考勤组当日的考勤班次
     */
    public static AttendanceGroupClassVo getGroupClass(Long groupId) {
        return getGroupClass(groupId, new Date());
    }

    /**
     * 根据考勤组ID获取指定日期的考勤班次信息
     *
     * @param groupId 考勤组ID
     * @param date    日期
     * @return 考勤组指定日期的考勤班次
     */
    public static AttendanceGroupClassVo getGroupClass(Long groupId, Date date) {
        List<AttendanceGroupClassVo> groupClasses = getGroupClasses(groupId);
        if (CollUtil.isNotEmpty(groupClasses)) {
            int week = DateUtils.getWeekOfDate(date);
            for (AttendanceGroupClassVo groupClass : groupClasses) {
                if (groupClass.getWeekly() == week) {
                    return groupClass;
                }
            }
        }
        return null;
    }

    public static AttendanceGroupClassVo getGroupClass(Long groupId, String date) {
        return getGroupClass(groupId, DateUtils.parseDate(date));
    }

    /**
     * 获取考勤组下指定的考勤班次
     *
     * @param groupId 考勤组ID
     * @param classId 考勤班次ID
     * @return
     */
    public static AttendanceGroupClassVo getGroupClass(Long groupId, Long classId) {
        List<AttendanceGroupClassVo> groupClasses = getGroupClasses(groupId);
        if (CollUtil.isNotEmpty(groupClasses) && classId != null) {
            for (AttendanceGroupClassVo groupClass : groupClasses) {
                if (groupClass.getClassesId().equals(classId)) {
                    return groupClass;
                }
            }
        }
        return null;
    }

    /**
     * 根据考勤组ID获取考勤班次集合
     *
     * @param groupId 考勤组ID
     * @return
     */
    public static List<AttendanceGroupClassVo> getGroupClasses(Long groupId) {
        String key = getKey(CacheTypeEnum.groupClasses, groupId);
        List<AttendanceGroupClassVo> groupClasses = null;
        if (CollUtil.isEmpty(groupClasses)) {
            groupClasses = groupClassesMapper.selectByGroupId(groupId);
            if (CollUtil.isNotEmpty(groupClasses)) {
                RedisUtils.setCacheObject(key, groupClasses);
            }
        }
        return groupClasses;
    }

    /**
     * 清除所有考勤班次缓存
     *
     * @return
     */
    public static void removeGroupClassesCache() {
        RedisUtils.deleteKeys(getKey(CacheTypeEnum.groupClasses, "*"));
    }

    /**
     * 根据考勤组ID清除班次缓存
     *
     * @param groupId 考勤组ID
     * @return
     */
    public static boolean removeGroupClassesCache(Long groupId) {
        return RedisUtils.deleteObject(getKey(CacheTypeEnum.groupClasses, groupId));
    }

    /**
     * 根据考勤组ID获取考勤区域
     *
     * @param groupId
     * @return
     */
    public static List<AttendanceGroupAreaVo> getGroupAreas(Long groupId) {
        String key = getKey(CacheTypeEnum.area, groupId);
        List<AttendanceGroupAreaVo> areas = RedisUtils.getCacheObject(key);
        if (CollUtil.isEmpty(areas)) {
            areas = groupAreaMapper.selectByGroupId(groupId);
            if (CollUtil.isNotEmpty(areas)) {
                RedisUtils.setCacheObject(key, areas);
            }
        }
        return areas;
    }

    public static boolean removeGroupAreasCache(Long groupId) {
        return RedisUtils.deleteObject(getKey(CacheTypeEnum.area, groupId));
    }

    /**
     * 根据班次ID获取班次
     *
     * @param id 班次ID
     * @return 班次信息
     */
    public static AttendanceClasses getClassesById(Long id) {
        String key = getKey(CacheTypeEnum.classes, id);
        AttendanceClasses classes = RedisUtils.getCacheObject(key);
        if (classes == null) {
            initClassesCache();
            classes = RedisUtils.getCacheObject(key);
        }
        return classes;
    }

    /**
     * 清空班次缓存
     *
     * @return 成功状态
     */
    public static boolean removeClassesCache() {
        return RedisUtils.deleteObject(getKey(CacheTypeEnum.classes, "*"));
    }

    /**
     * 根据班次ID清除缓存
     *
     * @param classesId 班次ID
     * @return
     */
    public static boolean removeClassesCache(Long classesId) {
        return RedisUtils.deleteObject(getKey(CacheTypeEnum.classes, classesId));
    }

    /**
     * 加载所有班次缓存
     */
    public static void initClassesCache() {
        if(CronUtil.getScheduler().isStarted()) {
            CronUtil.stop();
        }

        String key = getKey(CacheTypeEnum.classes, "");
        List<AttendanceClasses> attendanceClasses = classesMapper.selectList();
        for (Map.Entry<Long, Set<String>> entity : scheduleIds.entrySet()) {
            for (String scheduleId : entity.getValue()) {
                CronUtil.remove(scheduleId);
            }
        }
        for (AttendanceClasses attendanceClass : attendanceClasses) {
            initClassesCache(attendanceClass);
        }
        CronUtil.start();
    }

    /**
     * 根据班次ID加载班次缓存
     *
     * @param classesId 班次ID
     */
    public static void initClassesCache(Long classesId) {
        AttendanceClasses attendanceClass = classesMapper.selectById(classesId);
        if (scheduleIds.containsKey(classesId)) {
            Set<String> set = scheduleIds.get(classesId);
            for (String scheduleId : set) {
                CronUtil.remove(scheduleId);
            }
            scheduleIds.remove(classesId);
        }
        initClassesCache(attendanceClass);
    }

    /**
     * 根据班次加载班次缓存
     *
     * @param attendanceClass 班次
     */
    public static void initClassesCache(AttendanceClasses attendanceClass) {
        if (attendanceClass != null) {

            String endWorkScheduleId = CronUtil.getScheduler().setMatchSecond(true).schedule(StringUtils.getDailyCron(0, 0, 0), (Task) () -> {
                handleNoAttendance(attendanceClass.getId(), 1, "2", DateUtil.offsetDay(new Date(), -1));
            });
            scheduleIds.put(attendanceClass.getId(), Sets.newHashSet(endWorkScheduleId));
            if (attendanceClass.getWorkAbsMin() != null && attendanceClass.getWorkAbsMin() > 0) {
                DateTime dateTime = DateUtil.offsetMinute(DateUtil.parseTime(attendanceClass.getWorkTime()), attendanceClass.getWorkAbsMin());
                String workScheduleId = CronUtil.getScheduler().setMatchSecond(true).schedule(StringUtils.getDailyCron(dateTime.hour(true), dateTime.minute(), dateTime.second()), (Task) () -> {
                    handleNoAttendance(attendanceClass.getId(), 1, "1", DateUtil.date());
                });
                scheduleIds.get(attendanceClass.getId()).add(workScheduleId);
            }
            if (attendanceClass.getIsAutoAfter() != null && YesNoEnum.YES.getValue().equals(attendanceClass.getIsAutoAfter().toString())) {
                DateTime dateTime = DateUtil.parseTime(attendanceClass.getAfterTime());
                String autoAttendScheduleId = CronUtil.getScheduler().setMatchSecond(true).schedule(StringUtils.getDailyCron(dateTime.hour(true), dateTime.minute(), dateTime.second()), (Task) () -> {
                    handleAutoAttendance(attendanceClass.getId(), 1, DateUtil.offsetDay(new Date(), -1));
                });
                scheduleIds.get(attendanceClass.getId()).add(autoAttendScheduleId);
            }

            removeClassesCache(attendanceClass.getId());
            RedisUtils.setCacheObject(getKey(CacheTypeEnum.classes, attendanceClass.getId()), attendanceClass);
        }
    }

    /**
     * 获取扫码签到认证签名
     *
     * @param qrCodeInfo 签名数据
     * @param timeout    有效期（秒）， 默认1小时
     * @return 签名
     */
    public static String getSign(AttendanceQrCodeInfo qrCodeInfo, Integer timeout) {
        String sign = IdUtil.fastSimpleUUID();
        String key = getKey(CacheTypeEnum.qrCode, sign);
        RedisUtils.setCacheObject(key, qrCodeInfo);
        if (timeout == null || timeout <= 0) {
            timeout = 60 * 60;
        }
        RedisUtils.expire(key, timeout);
        return sign;
    }

    /**
     * 签名认证，获取认证内容
     *
     * @param sign 签名
     * @return
     */
    public static AttendanceQrCodeInfo verifySign(String sign) {
        if (StrUtil.isNotBlank(sign)) {
            String key = getKey(CacheTypeEnum.qrCode, sign);
            return RedisUtils.getCacheObject(key);
        }
        return null;
    }

    /**
     * 判断当日是否需要打卡
     *
     * @param groupId 考勤组ID
     * @param date    日期字符串， 格式 yyyy-MM-dd
     * @return
     */
    public static boolean checkNeedAttendance(Long groupId, String date) {
        return checkNeedAttendance(groupId, DateUtils.parseDate(date));
    }

    /**
     * 判断当日是否需要打卡
     *
     * @param groupId 考勤组ID
     * @param date    日期
     * @return
     */
    public static boolean checkNeedAttendance(Long groupId, Date date) {
        AttendanceGroupClassVo groupClass = getGroupClass(groupId);
        if(groupClass == null) {
            return false;
        }
        AttendanceGroup group = getGroup(groupId);
        if (YesNoEnum.YES.getName().equals(groupClass.getStatus())) {
            if (YesNoEnum.YES.getValue().equals(group.getHolidayLeave())) {
                // 需要打卡时， 判断是否节假日
                return !HolidayUtils.checkHoliday(DateUtil.formatDate(date));
            }
            return true;
        }
        if (YesNoEnum.YES.getValue().equals(group.getHolidayLeave())) {
            // 无需打卡时， 判断是否调休打卡
            return HolidayUtils.checkWork(DateUtil.formatDate(date));
        }
        return false;
    }

    /**
     * 获取需要打卡的天数
     *
     * @param groupId   考勤组ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 天数
     */
    public static int getNeedAttendanceDays(Long groupId, String startDate, String endDate) {
        DateTime start = DateUtil.parseDate(startDate);
        DateTime end = DateUtil.parseDate(endDate);
        int days = 0;
        while (start.before(end)) {
            if (checkNeedAttendance(groupId, start)) {
                days++;
            }
            start = DateUtil.offsetDay(start, 1);
        }
        return days;
    }

    /**
     * 处理考勤记录状态
     *
     * @param flow       考勤记录数据
     * @param groupClass 考勤班次
     */
    public static void handlerAttendStatus(AttendanceFlow flow, AttendanceGroupClassVo groupClass) {
        Date nowDate = new DateTime();
        if (ObjectUtil.isNull(groupClass)) {
            flow.setAttendStatus(AttendanceStatusEnum.normal.getStatus());
        } else {
            if (AttendanceKindEnum.start_work.getType().equals(flow.getAttendKind())) {
                int offsetSeconds = DateUtils.getOffsetSeconds(DateUtil.parse(DateUtil.formatDate(nowDate) + groupClass.getWorkTime(), "yyyy-MM-ddHH:mm:ss"), nowDate);
                // 超时打卡优先判断是否记缺卡， 然后判断是否严重迟到，如果都不满足条件则记为迟到
                if (offsetSeconds <= 0) {
                    flow.setAttendStatus(AttendanceStatusEnum.normal.getStatus());
                } else if (groupClass.getWorkAbsMin() != null && groupClass.getWorkAbsMin() > 0 && offsetSeconds > groupClass.getWorkAbsMin() * 60) {
                    flow.setAttendStatus(AttendanceStatusEnum.away_work.getStatus());
                    flow.setErrMinutes(NumberUtil.ceilDiv(offsetSeconds, 60));
                } else if (YesNoEnum.YES.getValue().equals(groupClass.getIsSeriousLate().toString()) && groupClass.getWorkSeriousLateMin() != null && groupClass.getWorkSeriousLateMin() > 0 && offsetSeconds > groupClass.getWorkSeriousLateMin() * 60) {
                    flow.setAttendStatus(AttendanceStatusEnum.serious_late.getStatus());
                    flow.setErrMinutes(NumberUtil.ceilDiv(offsetSeconds, 60));
                } else if (groupClass.getWorkLateMin() != null && groupClass.getWorkLateMin() > 0 && offsetSeconds > groupClass.getWorkLateMin() * 60) {
                    flow.setAttendStatus(AttendanceStatusEnum.late.getStatus());
                    flow.setErrMinutes(NumberUtil.ceilDiv(offsetSeconds, 60));
                } else {
                    // 如果都不满足，默认正常打卡
                    flow.setAttendStatus(AttendanceStatusEnum.normal.getStatus());
                }
            } else if (AttendanceKindEnum.end_work.getType().equals(flow.getAttendKind())) {
                int offsetSeconds = DateUtils.getOffsetSeconds(nowDate, DateUtil.parse(DateUtil.formatDate(nowDate) + groupClass.getAfterTime(), "yyyy-MM-ddHH:mm:ss"));
                if (offsetSeconds <= 0) {
                    flow.setAttendStatus(AttendanceStatusEnum.normal.getStatus());
                } else if (groupClass.getAfterAbsMin() != null && groupClass.getAfterAbsMin() > 0 && offsetSeconds > groupClass.getAfterAbsMin() * 60) {
                    flow.setAttendStatus(AttendanceStatusEnum.away_work.getStatus());
                    flow.setErrMinutes(NumberUtil.ceilDiv(offsetSeconds, 60));
                } else if (groupClass.getAfterLeaveEarly() != null && groupClass.getAfterLeaveEarly() > 0 && offsetSeconds > groupClass.getAfterLeaveEarly() * 60) {
                    flow.setAttendStatus(AttendanceStatusEnum.early.getStatus());
                    flow.setErrMinutes(NumberUtil.ceilDiv(offsetSeconds, 60));
                } else {
                    // 如果都不满足，默认正常打卡
                    flow.setAttendStatus(AttendanceStatusEnum.normal.getStatus());
                }
            } else {
                flow.setAttendStatus(AttendanceStatusEnum.normal.getStatus());
            }
        }
    }

    public static AttendanceFlowCountDetailByDayVo getUserFlowCountForOneDay(List<AttendanceFlowVo> flows) {
        AttendanceFlowCountDetailByDayVo data = new AttendanceFlowCountDetailByDayVo();
        if (CollUtil.isNotEmpty(flows)) {
            AttendanceFlowVo firstFlow = flows.get(0);
            data.setUserId(flows.get(0).getUserId());
            data.setUsername(UserUtils.getRealName(flows.get(0).getUserId()));

            Set<Integer> statusList = new HashSet<>();

            double workSeconds = 0;
            int lateNum = 0;
            int seriousLateNum = 0;
            int earlyNum = 0;
            int noAttendNum = 0;
            int outsideNum = 0;
            String attendFlag = YesNoEnum.NO.getValue();
            String outsideFlag = YesNoEnum.NO.getValue();
            Table<Integer, String, AttendanceFlowVo> flowTable = HashBasedTable.create();
            for (AttendanceFlowVo flow : flows) {
                if (flow.getAttendStatus() == AttendanceStatusEnum.normal.getStatus()) {
                    attendFlag = YesNoEnum.YES.getValue();
                }
                if (flow.getAttendStatus() == AttendanceStatusEnum.late.getStatus()) {
                    lateNum++;
                    statusList.add(flow.getAttendStatus());
                    attendFlag = YesNoEnum.YES.getValue();
                } else if (flow.getAttendStatus() == AttendanceStatusEnum.serious_late.getStatus()) {
                    seriousLateNum++;
                    statusList.add(flow.getAttendStatus());
                    attendFlag = YesNoEnum.YES.getValue();
                } else if (flow.getAttendStatus() == AttendanceStatusEnum.early.getStatus()) {
                    earlyNum++;
                    statusList.add(flow.getAttendStatus());
                    attendFlag = YesNoEnum.YES.getValue();
                } else if (flow.getAttendStatus() == AttendanceStatusEnum.away_work.getStatus()) {
                    noAttendNum++;
                    statusList.add(flow.getAttendStatus());
                }
                if (YesNoEnum.YES.getValue().equals(flow.getAreaOutside().toString())) {
                    outsideNum++;
                    outsideFlag = YesNoEnum.YES.getValue();
                }
                if (flow.getAttendStatus() != AttendanceStatusEnum.away_work.getStatus() && StrUtil.isNotBlank(flow.getAttendTime())) {
                    AttendanceFlowVo reverseFlow = flowTable.get(flow.getAttendNumber(), AttendanceKindEnum.getReverseType(flow.getAttendKind()));
                    if (reverseFlow != null) {
                        workSeconds += DateUtils.getOffsetSeconds(DateUtil.parseTime(flow.getAttendTime()), DateUtil.parseTime(reverseFlow.getAttendTime()));
                    } else {
                        flowTable.put(flow.getAttendNumber(), flow.getAttendKind(), flow);
                    }
                }
            }
            if (statusList.isEmpty()) {
                statusList.add(AttendanceStatusEnum.normal.getStatus());
            }
            if (YesNoEnum.YES.getValue().equals(outsideFlag)) {
                statusList.add(AttendanceStatusEnum.outside.getStatus());
            }
            data.setUserId(firstFlow.getUserId())
                    .setUsername(UserUtils.getUserName(firstFlow.getUserId()))
                    .setLateNum(lateNum)
                    .setSeriousLateNum(seriousLateNum)
                    .setEarlyNum(earlyNum)
                    .setNoAttendNum(noAttendNum)
                    .setOutsideNum(outsideNum)
                    .setWorkHours(NumberUtil.div(workSeconds, 3600, 1))
                    .setAttendFlag(attendFlag)
                    .setAttendStatus(statusList.stream().sorted().map(AttendanceStatusEnum::getNameByStatus).collect(Collectors.joining(",")))
            ;
        } else {
            data.setAttendStatus("未打卡");
        }
        return data;
    }

    public static AttendanceFlowCountDetailByDateRangeVo getUserFlowCountForDateRange(Long groupId, String startDate, String endDate, List<AttendanceFlowVo> flows) {
        AttendanceFlowCountDetailByDateRangeVo data = new AttendanceFlowCountDetailByDateRangeVo();
        data.setNeedAttendDays(getNeedAttendanceDays(groupId, startDate, endDate));
        if (CollUtil.isNotEmpty(flows)) {
            Map<String, List<AttendanceFlowVo>> flowMap = flows.stream().collect(Collectors.groupingBy(AttendanceFlowVo::getAttendDate, LinkedHashMap::new, Collectors.toList()));
            for (Map.Entry<String, List<AttendanceFlowVo>> flowEntity : flowMap.entrySet()) {
                List<AttendanceFlowVo> oneDayFlows = flowEntity.getValue();
                AttendanceFlowCountDetailByDayVo countOneDay = getUserFlowCountForOneDay(oneDayFlows);
                data.setWorkHours(data.getWorkHours() + countOneDay.getWorkHours());
                data.setLateNum(data.getLateNum() + countOneDay.getLateNum());
                data.setSeriousLateNum(data.getSeriousLateNum() + countOneDay.getSeriousLateNum());
                data.setEarlyNum(data.getEarlyNum() + countOneDay.getEarlyNum());
                data.setNoAttendNum(data.getNoAttendNum() + countOneDay.getNoAttendNum());
                data.setOutsideNum(data.getOutsideNum() + countOneDay.getOutsideNum());
                if (YesNoEnum.NO.getValue().equals(countOneDay.getAttendFlag())) {
                    data.setNoAttendDays(data.getNoAttendDays() + 1);
                }
            }
        }
        if (data.getNeedAttendDays() > 0) {
            data.setAverageWorkHours(NumberUtil.div(data.getAverageWorkHours(), data.getNeedAttendDays(), 1));
        }
        return data;
    }

    /**
     * 处理缺卡数据
     *
     * @param classesId    考勤班次
     * @param attendNumber 考勤次数
     * @param attendKind   考勤类型
     */
    public static void handleNoAttendance(Long classesId, Integer attendNumber, String attendKind, DateTime date) {
        int week = DateUtils.getWeekOfDate(date);
        List<AttendanceGroupClasses> groupClasses = groupClassesMapper.getGroupIds(classesId, week);
        groupClasses.parallelStream().forEach(classes -> {
            if (checkNeedAttendance(classes.getGroupId(), date)) {
                attendanceFlowMapper.handleNoAttendanceFlow(classes.getId(), date.toDateStr(), attendNumber, attendKind);
            }
        });
    }

    /**
     * 处理下班自动打卡数据
     *
     * @param classesId    考勤班次
     * @param attendNumber 考勤次数
     */
    public static void handleAutoAttendance(Long classesId, Integer attendNumber, DateTime date) {
        int week = DateUtils.getWeekOfDate(date);
        List<AttendanceGroupClasses> groupClasses = groupClassesMapper.getGroupIds(classesId, week);
        groupClasses.parallelStream().forEach(classes -> {
            if (checkNeedAttendance(classes.getGroupId(), date)) {
                attendanceFlowMapper.handleAutoAttendanceFlow(classes.getId(), date.toDateStr(), attendNumber, AttendanceKindEnum.end_work.getType());
            }
        });
    }

    /**
     * 根据缓存类型和
     *
     * @param type 缓存类型
     * @param key  缓存后缀关键字
     * @return 缓存key
     */
    private static String getKey(CacheTypeEnum type, Object key) {
        return AttendanceCacheNames.ATTENDANCE_CONFIG_CACHE + type + ":" + key;
    }

    /**
     * 考勤数据类型
     */
    private enum CacheTypeEnum {
        group,
        area,
        classes,
        groupClasses,
        users,
        qrCode
    }
}
