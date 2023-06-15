package com.hospital.attendance.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.demo.hospital.attendance.dao.*;
import com.demo.hospital.attendance.enums.AttendanceKindEnum;
import com.demo.hospital.attendance.enums.AttendanceStatusEnum;
import com.demo.hospital.attendance.model.*;
import com.demo.hospital.attendance.model.vo.AttendanceFlowCountDetailByDateRangeVO;
import com.demo.hospital.attendance.model.vo.AttendanceFlowCountDetailByDayVO;
import com.demo.hospital.attendance.model.vo.AttendanceGroupClassVO;
import com.demo.hospital.common.redis.RedisUtils;
import com.demo.hospital.common.security.UserUtil;
import com.demo.hospital.common.util.CronUtils;
import com.demo.hospital.common.util.DateUtils;
import com.demo.hospital.common.util.NumberUtils;
import com.demo.hospital.common.util.SpringUtils;
import com.demo.hospital.systemcode.enums.YesNoEnum;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 考勤工具类
 *
 * @author liguoxian
 */
public class AttendanceUtils {

    private static final Map<Integer, Set<String>> scheduleIds = new HashMap<>();
    private static final String ATTEND_CONFIG_PREFIX = "attendance:config:";
    private static final AttendanceClassesDao classesDao = SpringUtils.getBean(AttendanceClassesDao.class);
    private static final AttendanceGroupDao groupDao = SpringUtils.getBean(AttendanceGroupDao.class);
    private static final AttendanceGroupClassesDao groupClassesDao = SpringUtils.getBean(AttendanceGroupClassesDao.class);
    private static final AttendanceGroupAreaDao groupAreaDao = SpringUtils.getBean(AttendanceGroupAreaDao.class);
    private static final AttendanceGroupUserDao groupUserDao = SpringUtils.getBean(AttendanceGroupUserDao.class);
    private static final AttendanceFlowDao attendanceFlowDao = SpringUtils.getBean(AttendanceFlowDao.class);

    /**
     * 根据用户ID获取对应的考勤组信息集合
     *
     * @param userId 用户ID
     * @return 用户所在的考勤组
     */
    public static List<AttendanceGroup> getMyGroup(Integer userId) {
        List<Integer> groupIds = getMyGroupId(userId);
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
    public static List<Integer> getMyGroupId(Integer userId) {
        String userKey = getKey(CacheTypeEnum.users, "users");
        Map<Integer, List<Integer>> userMap = RedisUtils.getCacheObject(userKey);
        if (userMap == null) {
            initUserGroupCache();
            userMap = RedisUtils.getCacheObject(userKey);
        }
        return userMap.get(userId);
    }

    /**
     * 根据考勤组ID获取对应的考勤用户ID集合
     *
     * @param groupId 考勤组ID
     * @return 在该考勤组的用户
     */
    public static List<Integer> getUsersByGroupId(Integer groupId) {
        String groupKey = getKey(CacheTypeEnum.users, "groups");
        Map<Integer, List<Integer>> groupMap = RedisUtils.getCacheObject(groupKey);
        if (groupMap == null) {
            initUserGroupCache();
            groupMap = RedisUtils.getCacheObject(groupKey);
        }
        return groupMap.get(groupId);
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
        List<AttendanceGroupUser> groupUsers = groupUserDao.selectAll();
        Map<Integer, List<Integer>> userMap = groupUsers.stream()
                .collect(Collectors.groupingBy(AttendanceGroupUser::getUserId, Collectors.mapping(AttendanceGroupUser::getGroupId, Collectors.toList())));

        Map<Integer, List<Integer>> groupMap = groupUsers.stream()
                .collect(Collectors.groupingBy(AttendanceGroupUser::getGroupId, Collectors.mapping(AttendanceGroupUser::getGroupId, Collectors.toList())));
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
    public static AttendanceGroup getGroup(Integer groupId) {
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
     *
     * @return 成功状态
     */
    public static boolean removeGroupCache() {
        Collection<String> keys = RedisUtils.keys(getKey(CacheTypeEnum.group, "*"));
        long size = RedisUtils.deleteObject(keys);
        return keys.size() == size;
    }


    /**
     * 根据ID清除考勤组缓存
     *
     * @param groupId 考勤组ID
     * @return 成功状态
     */
    public static boolean removeGroupCache(Integer groupId) {
        return RedisUtils.deleteObject(getKey(CacheTypeEnum.group, groupId));
    }

    /**
     * 更新所有考勤组缓存
     */
    public static void initGroupCache() {
        List<AttendanceGroup> attendanceGroups = groupDao.selectAll();
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
    public static void initGroupCache(Integer groupId) {
        AttendanceGroup group = groupDao.selectByPrimaryKey(groupId);
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
    public static AttendanceGroupClassVO getGroupClass(Integer groupId) {
        return getGroupClass(groupId, new Date());
    }

    /**
     * 根据考勤组ID获取指定日期的考勤班次信息
     *
     * @param groupId 考勤组ID
     * @param date    日期
     * @return 考勤组指定日期的考勤班次
     */
    public static AttendanceGroupClassVO getGroupClass(Integer groupId, Date date) {
        List<AttendanceGroupClassVO> groupClasses = getGroupClasses(groupId);
        if (CollUtil.isNotEmpty(groupClasses)) {
            int week = DateUtils.getWeekOfDate(date);
            for (AttendanceGroupClassVO groupClass : groupClasses) {
                if (groupClass.getWeekly() == week) {
                    return groupClass;
                }
            }
        }
        return null;
    }

    public static AttendanceGroupClassVO getGroupClass(Integer groupId, String date) {
        return getGroupClass(groupId, DateUtils.parseDate(date));
    }

    /**
     * 获取考勤组下指定的考勤班次
     *
     * @param groupId 考勤组ID
     * @param classId 考勤班次ID
     * @return
     */
    public static AttendanceGroupClassVO getGroupClass(Integer groupId, Integer classId) {
        List<AttendanceGroupClassVO> groupClasses = getGroupClasses(groupId);
        if (CollUtil.isNotEmpty(groupClasses) && classId != null) {
            for (AttendanceGroupClassVO groupClass : groupClasses) {
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
    public static List<AttendanceGroupClassVO> getGroupClasses(Integer groupId) {
        String key = getKey(CacheTypeEnum.groupClasses, groupId);
        List<AttendanceGroupClassVO> groupClasses = RedisUtils.getCacheObject(key);
        if (CollUtil.isEmpty(groupClasses)) {
            groupClasses = groupClassesDao.selectByGroupId(groupId);
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
    public static boolean removeGroupClassesCache() {
        Collection<String> keys = RedisUtils.keys(getKey(CacheTypeEnum.groupClasses, "*"));
        long size = RedisUtils.deleteObject(keys);
        return keys.size() == size;
    }

    /**
     * 根据考勤组ID清除班次缓存
     *
     * @param groupId 考勤组ID
     * @return
     */
    public static boolean removeGroupClassesCache(Integer groupId) {
        return RedisUtils.deleteObject(getKey(CacheTypeEnum.groupClasses, groupId));
    }

    /**
     * 根据考勤组ID获取考勤区域
     *
     * @param groupId
     * @return
     */
    public static List<AttendanceGroupArea> getGroupAreas(Integer groupId) {
        String key = getKey(CacheTypeEnum.area, groupId);
        List<AttendanceGroupArea> areas = RedisUtils.getCacheObject(key);
        if (CollUtil.isEmpty(areas)) {
            areas = groupAreaDao.selectByGroupId(groupId);
            if (CollUtil.isNotEmpty(areas)) {
                RedisUtils.setCacheObject(key, areas);
            }
        }
        return areas;
    }

    public static boolean removeGroupAreasCache(Integer groupId) {
        return RedisUtils.deleteObject(getKey(CacheTypeEnum.area, groupId));
    }

    /**
     * 根据班次ID获取班次
     *
     * @param id 班次ID
     * @return 班次信息
     */
    public static AttendanceClasses getClassesById(Integer id) {
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
    public static boolean removeClassesCache(Integer classesId) {
        return RedisUtils.deleteObject(getKey(CacheTypeEnum.classes, classesId));
    }

    /**
     * 加载所有班次缓存
     */
    public static void initClassesCache() {
        String key = getKey(CacheTypeEnum.classes, "");
        List<AttendanceClasses> attendanceClasses = classesDao.selectAll();
        for (Map.Entry<Integer, Set<String>> entity : scheduleIds.entrySet()) {
            for (String scheduleId : entity.getValue()) {
                CronUtils.remove(scheduleId);
            }
        }
        for (AttendanceClasses attendanceClass : attendanceClasses) {
            initClassesCache(attendanceClass);
        }
        CronUtils.start();
    }

    /**
     * 根据班次ID加载班次缓存
     *
     * @param classesId 班次ID
     */
    public static void initClassesCache(Integer classesId) {
        AttendanceClasses attendanceClass = classesDao.selectByPrimaryKey(classesId);
        if (scheduleIds.containsKey(classesId)) {
            Set<String> set = scheduleIds.get(classesId);
            for (String scheduleId : set) {
                CronUtils.remove(scheduleId);
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
            String endWorkScheduleId = CronUtil.getScheduler().setMatchSecond(true).schedule(CronUtils.getDailyCron(0, 0, 0), (Task) () -> {
                handleNoAttendance(attendanceClass.getId(), 1, "2", DateUtils.offsetDay(new Date(), -1));
            });
            scheduleIds.put(attendanceClass.getId(), Sets.newHashSet(endWorkScheduleId));
            if (attendanceClass.getWorkAbsMin() != null && attendanceClass.getWorkAbsMin() > 0) {
                DateTime dateTime = DateUtils.offsetMinute(DateUtils.parseTime(attendanceClass.getWorkTime()), attendanceClass.getWorkAbsMin());
                String workScheduleId = CronUtil.getScheduler().setMatchSecond(true).schedule(CronUtils.getDailyCron(dateTime.hour(true), dateTime.minute(), dateTime.second()), (Task) () -> {
                    handleNoAttendance(attendanceClass.getId(), 1, "1", DateUtils.date());
                });
                scheduleIds.get(attendanceClass.getId()).add(workScheduleId);
            }
            if (attendanceClass.getIsAutoAfter() != null && YesNoEnum.yes.getValue().equals(attendanceClass.getIsAutoAfter().toString())) {
                DateTime dateTime = DateUtils.parseTime(attendanceClass.getAfterTime());
                String autoAttendScheduleId = CronUtil.getScheduler().setMatchSecond(true).schedule(CronUtils.getDailyCron(dateTime.hour(true), dateTime.minute(), dateTime.second()), (Task) () -> {
                    handleAutoAttendance(attendanceClass.getId(), 1, DateUtils.offsetDay(new Date(), -1));
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
    public static boolean checkNeedAttendance(Integer groupId, String date) {
        return checkNeedAttendance(groupId, DateUtils.parseDate(date));
    }

    /**
     * 判断当日是否需要打卡
     *
     * @param groupId 考勤组ID
     * @param date    日期
     * @return
     */
    public static boolean checkNeedAttendance(Integer groupId, Date date) {
        AttendanceGroupClassVO groupClass = getGroupClass(groupId);
        AttendanceGroup group = getGroup(groupId);
        if (YesNoEnum.yes.getIntValue().equals(groupClass.getStatus())) {
            if (YesNoEnum.yes.getIntValue().equals(group.getHolidayLeave())) {
                // 需要打卡时， 判断是否节假日
                return !HolidayUtils.checkHoliday(DateUtils.formatDate(date));
            }
            return true;
        }
        if (YesNoEnum.yes.getIntValue().equals(group.getHolidayLeave())) {
            // 无需打卡时， 判断是否调休打卡
            return HolidayUtils.checkWork(DateUtils.formatDate(date));
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
    public static int getNeedAttendanceDays(Integer groupId, String startDate, String endDate) {
        DateTime start = DateUtils.parseDate(startDate);
        DateTime end = DateUtils.parseDate(endDate);
        int days = 0;
        while (start.before(end)) {
            if (checkNeedAttendance(groupId, start)) {
                days++;
            }
            start = DateUtils.offsetDay(start, 1);
        }
        return days;
    }

    /**
     * 处理考勤记录状态
     *
     * @param flow       考勤记录数据
     * @param groupClass 考勤班次
     */
    public static void handlerAttendStatus(AttendanceFlow flow, AttendanceGroupClassVO groupClass) {
        Date nowDate = new DateTime();
        if (ObjectUtil.isNull(groupClass)) {
            flow.setAttendStatus(AttendanceStatusEnum.normal.getStatus());
        } else {
            if (AttendanceKindEnum.start_work.getType().equals(flow.getAttendKind())) {
                int offsetSeconds = DateUtils.getOffsetSeconds(DateUtils.parse(DateUtils.formatDate(nowDate) + groupClass.getWorkTime(), "yyyy-MM-ddHH:mm:ss"), nowDate);
                // 超时打卡优先判断是否记缺卡， 然后判断是否严重迟到，如果都不满足条件则记为迟到
                if (offsetSeconds <= 0) {
                    flow.setAttendStatus(AttendanceStatusEnum.normal.getStatus());
                } else if (groupClass.getWorkAbsMin() != null && groupClass.getWorkAbsMin() > 0 && offsetSeconds > groupClass.getWorkAbsMin() * 60) {
                    flow.setAttendStatus(AttendanceStatusEnum.away_work.getStatus());
                    flow.setErrMinutes(NumberUtils.ceilDiv(offsetSeconds, 60));
                } else if (YesNoEnum.yes.getValue().equals(groupClass.getIsSeriousLate().toString()) && groupClass.getWorkSeriousLateMin() != null && groupClass.getWorkSeriousLateMin() > 0 && offsetSeconds > groupClass.getWorkSeriousLateMin() * 60) {
                    flow.setAttendStatus(AttendanceStatusEnum.serious_late.getStatus());
                    flow.setErrMinutes(NumberUtils.ceilDiv(offsetSeconds, 60));
                } else if (groupClass.getWorkLateMin() != null && groupClass.getWorkLateMin() > 0 && offsetSeconds > groupClass.getWorkLateMin() * 60) {
                    flow.setAttendStatus(AttendanceStatusEnum.late.getStatus());
                    flow.setErrMinutes(NumberUtils.ceilDiv(offsetSeconds, 60));
                } else {
                    // 如果都不满足，默认正常打卡
                    flow.setAttendStatus(AttendanceStatusEnum.normal.getStatus());
                }
            } else if (AttendanceKindEnum.end_work.getType().equals(flow.getAttendKind())) {
                int offsetSeconds = DateUtils.getOffsetSeconds(nowDate, DateUtils.parse(DateUtils.formatDate(nowDate) + groupClass.getAfterTime(), "yyyy-MM-ddHH:mm:ss"));
                if (offsetSeconds <= 0) {
                    flow.setAttendStatus(AttendanceStatusEnum.normal.getStatus());
                } else if (groupClass.getAfterAbsMin() != null && groupClass.getAfterAbsMin() > 0 && offsetSeconds > groupClass.getAfterAbsMin() * 60) {
                    flow.setAttendStatus(AttendanceStatusEnum.away_work.getStatus());
                    flow.setErrMinutes(NumberUtils.ceilDiv(offsetSeconds, 60));
                } else if (groupClass.getAfterLeaveEarly() != null && groupClass.getAfterLeaveEarly() > 0 && offsetSeconds > groupClass.getAfterLeaveEarly() * 60) {
                    flow.setAttendStatus(AttendanceStatusEnum.early.getStatus());
                    flow.setErrMinutes(NumberUtils.ceilDiv(offsetSeconds, 60));
                } else {
                    // 如果都不满足，默认正常打卡
                    flow.setAttendStatus(AttendanceStatusEnum.normal.getStatus());
                }
            } else {
                flow.setAttendStatus(AttendanceStatusEnum.normal.getStatus());
            }
        }
    }

    public static AttendanceFlowCountDetailByDayVO getUserFlowCountForOneDay(List<AttendanceFlow> flows) {
        AttendanceFlowCountDetailByDayVO data = new AttendanceFlowCountDetailByDayVO();
        if (CollUtil.isNotEmpty(flows)) {
            AttendanceFlow firstFlow = flows.get(0);
            data.setUserId(flows.get(0).getUserId());
            data.setUsername(UserUtil.getUser(flows.get(0).getUserId()).getUserName());

            Set<Integer> statusList = new HashSet<>();

            double workSeconds = 0;
            int lateNum = 0;
            int seriousLateNum = 0;
            int earlyNum = 0;
            int noAttendNum = 0;
            int outsideNum = 0;
            String attendFlag = YesNoEnum.no.getValue();
            String outsideFlag = YesNoEnum.no.getValue();
            Table<Integer, String, AttendanceFlow> flowTable = HashBasedTable.create();
            for (AttendanceFlow flow : flows) {
                if (flow.getAttendStatus() == AttendanceStatusEnum.normal.getStatus()) {
                    attendFlag = YesNoEnum.yes.getValue();
                }
                if (flow.getAttendStatus() == AttendanceStatusEnum.late.getStatus()) {
                    lateNum++;
                    statusList.add(flow.getAttendStatus());
                    attendFlag = YesNoEnum.yes.getValue();
                } else if (flow.getAttendStatus() == AttendanceStatusEnum.serious_late.getStatus()) {
                    seriousLateNum++;
                    statusList.add(flow.getAttendStatus());
                    attendFlag = YesNoEnum.yes.getValue();
                } else if (flow.getAttendStatus() == AttendanceStatusEnum.early.getStatus()) {
                    earlyNum++;
                    statusList.add(flow.getAttendStatus());
                    attendFlag = YesNoEnum.yes.getValue();
                } else if (flow.getAttendStatus() == AttendanceStatusEnum.away_work.getStatus()) {
                    noAttendNum++;
                    statusList.add(flow.getAttendStatus());
                }
                if (YesNoEnum.yes.getValue().equals(flow.getAreaOutside().toString())) {
                    outsideNum++;
                    outsideFlag = YesNoEnum.yes.getValue();
                }
                if (flow.getAttendStatus() != AttendanceStatusEnum.away_work.getStatus() && StrUtil.isNotBlank(flow.getAttendTime())) {
                    AttendanceFlow reverseFlow = flowTable.get(flow.getAttendNumber(), AttendanceKindEnum.getReverseType(flow.getAttendKind()));
                    if (reverseFlow != null) {
                        workSeconds += DateUtils.getOffsetSeconds(DateUtils.parseTime(flow.getAttendTime()), DateUtils.parseTime(reverseFlow.getAttendTime()));
                    } else {
                        flowTable.put(flow.getAttendNumber(), flow.getAttendKind(), flow);
                    }
                }
            }
            if (statusList.isEmpty()) {
                statusList.add(AttendanceStatusEnum.normal.getStatus());
            }
            if (YesNoEnum.yes.getValue().equals(outsideFlag)) {
                statusList.add(AttendanceStatusEnum.outside.getStatus());
            }
            data.setUserId(firstFlow.getUserId())
                    .setUsername(UserUtil.getUsername(firstFlow.getUserId()))
                    .setLateNum(lateNum)
                    .setSeriousLateNum(seriousLateNum)
                    .setEarlyNum(earlyNum)
                    .setNoAttendNum(noAttendNum)
                    .setOutsideNum(outsideNum)
                    .setWorkHours(NumberUtils.div(workSeconds, 3600, 1))
                    .setAttendFlag(attendFlag)
                    .setAttendStatus(statusList.stream().sorted().map(AttendanceStatusEnum::getNameByStatus).collect(Collectors.joining(",")))
            ;
        } else {
            data.setAttendStatus("未打卡");
        }
        return data;
    }

    public static AttendanceFlowCountDetailByDateRangeVO getUserFlowCountForDateRange(Integer groupId, String startDate, String endDate, List<AttendanceFlow> flows) {
        AttendanceFlowCountDetailByDateRangeVO data = new AttendanceFlowCountDetailByDateRangeVO();
        data.setNeedAttendDays(getNeedAttendanceDays(groupId, startDate, endDate));
        if (CollUtil.isNotEmpty(flows)) {
            Map<String, List<AttendanceFlow>> flowMap = flows.stream().collect(Collectors.groupingBy(AttendanceFlow::getAttendDate, LinkedHashMap::new, Collectors.toList()));
            for (Map.Entry<String, List<AttendanceFlow>> flowEntity : flowMap.entrySet()) {
                List<AttendanceFlow> oneDayFlows = flowEntity.getValue();
                AttendanceFlowCountDetailByDayVO countOneDay = getUserFlowCountForOneDay(oneDayFlows);
                data.setWorkHours(data.getWorkHours() + countOneDay.getWorkHours());
                data.setLateNum(data.getLateNum() + countOneDay.getLateNum());
                data.setSeriousLateNum(data.getSeriousLateNum() + countOneDay.getSeriousLateNum());
                data.setEarlyNum(data.getEarlyNum() + countOneDay.getEarlyNum());
                data.setNoAttendNum(data.getNoAttendNum() + countOneDay.getNoAttendNum());
                data.setOutsideNum(data.getOutsideNum() + countOneDay.getOutsideNum());
                if (YesNoEnum.no.getValue().equals(countOneDay.getAttendFlag())) {
                    data.setNoAttendDays(data.getNoAttendDays() + 1);
                }
            }
        }
        if (data.getNeedAttendDays() > 0) {
            data.setAverageWorkHours(NumberUtils.div(data.getAverageWorkHours(), data.getNeedAttendDays(), 1));
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
    public static void handleNoAttendance(Integer classesId, Integer attendNumber, String attendKind, DateTime date) {
        int week = DateUtils.getWeekOfDate(date);
        List<AttendanceGroupClasses> groupClasses = groupClassesDao.getGroupIds(classesId, week);
        groupClasses.parallelStream().forEach(classes -> {
            if (checkNeedAttendance(classes.getGroupId(), date)) {
                attendanceFlowDao.handleNoAttendanceFlow(classes.getId(), date.toDateStr(), attendNumber, attendKind);
            }
        });
    }

    /**
     * 处理下班自动打卡数据
     *
     * @param classesId    考勤班次
     * @param attendNumber 考勤次数
     */
    public static void handleAutoAttendance(Integer classesId, Integer attendNumber, DateTime date) {
        int week = DateUtils.getWeekOfDate(date);
        List<AttendanceGroupClasses> groupClasses = groupClassesDao.getGroupIds(classesId, week);
        groupClasses.parallelStream().forEach(classes -> {
            if (checkNeedAttendance(classes.getGroupId(), date)) {
                attendanceFlowDao.handleAutoAttendanceFlow(classes.getId(), date.toDateStr(), attendNumber, AttendanceKindEnum.end_work.getType());
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
        return ATTEND_CONFIG_PREFIX + type + ":" + key;
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
