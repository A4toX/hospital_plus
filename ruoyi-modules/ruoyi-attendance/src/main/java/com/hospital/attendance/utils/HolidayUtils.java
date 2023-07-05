package com.hospital.attendance.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Joiner;
import com.hospital.attendance.constant.AttendanceCacheNames;
import com.hospital.attendance.domain.AttendanceHolidayConfig;
import com.hospital.attendance.mapper.AttendanceHolidayConfigMapper;
import com.hospital.attendance.utils.dto.Holiday;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.redis.utils.RedisUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 节假日工具类
 * @author liguoxian
 */
public class HolidayUtils {

    private static AttendanceHolidayConfigMapper attendanceHolidayConfigMapper = SpringUtils.getBean(AttendanceHolidayConfigMapper.class);

    private static ReentrantLock lock = new ReentrantLock();

    /**
     * 判断日期是否是节假日
     * @param date 日期
     * @return
     */
    public static boolean checkHoliday(String date) {
        initHolidayCache(date.substring(0, 4));
        String key = getKey(CacheTypeEnum.holiday, date);
        String holiday = RedisUtils.getCacheObject(key);
        return StrUtil.isNotBlank(holiday);
    }

    /**
     * 判断日期是否是节假日调休工作日
     * @param date 日期
     * @return
     */
    public static boolean checkWork(String date) {
        initHolidayCache(date.substring(0, 4));
        String key = getKey(CacheTypeEnum.work, date);
        String holiday = RedisUtils.getCacheObject(key);
        return StrUtil.isNotBlank(holiday);
    }

    private static void initHolidayCache(String year) {
        initHolidayCache(year, true);
    }

    /**
     * 初始化指定年份节假日数据
     * @param year 年份
     * @param nextYear 是否处理下年数据
     */
    private static void initHolidayCache(String year, boolean nextYear) {
        String key = getKey(CacheTypeEnum.init, year);
        String holiday = RedisUtils.getCacheObject(key);
        if(StrUtil.isBlank(holiday) && !lock.isLocked()) {
            List<AttendanceHolidayConfig> configs = attendanceHolidayConfigMapper.selectByYear(year);
            if(CollUtil.isEmpty(configs)) {
                lock.lock();
                try {
                    configs = updateByApi(year);
                } catch (Exception e) {
                    throw new ServiceException("同步处理假日数据失败");
                } finally {
                    lock.unlock();
                }
            }
            // 数据更新到缓存
            if(CollUtil.isNotEmpty(configs)) {
                for (AttendanceHolidayConfig config : configs) {
                    DateTime beginDate = DateUtil.parseDate(config.getBeginDate());
                    DateTime endDate = DateUtil.parseDate(config.getEndDate());
                    long days = DateUtil.betweenDay(beginDate, endDate, true);
                    for (int i = 0; i <= days; i++) {
                        String date = DateUtil.offsetDay(beginDate, i).toDateStr();
                        RedisUtils.setCacheObject(getKey(CacheTypeEnum.holiday, date), "节假日");
                    }

                    List<String> inverseDays = StrUtil.split(config.getInverseDays(), ',');
                    if(CollUtil.isNotEmpty(inverseDays)) {
                        inverseDays.stream()
                            .forEach(inverseDay -> {
                                RedisUtils.setCacheObject(getKey(CacheTypeEnum.work, inverseDay), "调休");
                            });
                    }
                }
            }
            RedisUtils.setCacheObject(key, "Y");
        }
        // 本年12月份，处理下一年数据
        if(nextYear && DateUtil.thisMonth() == 11) {
            initHolidayCache(DateUtil.nextMonth().toString("yyyy"), false);
        }
    }

    private static List<AttendanceHolidayConfig> updateByApi(String year) {
        JSON json = JSONUtil.parse(getData(year));
        List<JSONObject> jsonObjects = json.getByPath("showapi_res_body.data", List.class);
        List<AttendanceHolidayConfig> configs = new ArrayList<>();
        jsonObjects.forEach(object -> {
            String begin = object.get("begin", String.class);
            String end = object.get("end", String.class);
            List<String> inverseDays = object.get("inverse_days", List.class);
            DateTime beginDate = DateUtil.parse(begin, "yyyyMMdd");
            DateTime endDate = DateUtil.parse(end, "yyyyMMdd");
            long days = DateUtil.betweenDay(beginDate, endDate, true);
            if(days < 0 || days > 30) {
                throw new IllegalArgumentException();
            }
            AttendanceHolidayConfig config = new AttendanceHolidayConfig();
            config.setBelongYear(year);
            config.setBeginDate(beginDate.toDateStr());
            config.setEndDate(endDate.toDateStr());
            config.setHoliday(object.get("holiday", String.class));
            config.setHolidayRemark(object.get("holiday_remark", String.class));
            if(CollUtil.isNotEmpty(inverseDays)) {
                String inverseDayStr = inverseDays
                    .stream()
                    .map(day -> DateUtil.parse(day, "yyyyMMdd").toDateStr())
                    .collect(Collectors.joining(","));
                config.setInverseDays(inverseDayStr);
            }
            configs.add(config);

        });
        if(!configs.isEmpty()) {
            attendanceHolidayConfigMapper.insertBatch(configs);
        }
        return configs;
    }

    private static String getData(String year) {
        String host = "https://jiejiari.market.alicloudapi.com/holidayList";
        String appcode = "26ba8993074a40e78ae6daa533674e1e";
        HttpResponse response = HttpRequest.get(host)
                .header("Authorization", "APPCODE " + appcode)
                .form("year", year)
                .execute();

        return response.body();
    }

    private static String getKey(CacheTypeEnum type, Object key) {
        return AttendanceCacheNames.HOLIDAY_CACHE + type + ":" + key;
    }

    private enum CacheTypeEnum {
        holiday,
        work,
        init
    }
}
