package com.hospital.attendance.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.demo.hospital.attendance.utils.dto.Holiday;
import com.demo.hospital.common.constant.ErrCode;
import com.demo.hospital.common.exceptions.BizException;
import com.demo.hospital.common.redis.RedisUtils;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 节假日工具类
 * @author liguoxian
 */
public class HolidayUtils {

    private static final String HOLIDAY_PREFIX = "holiday:";
    private static ReentrantLock lock = new ReentrantLock();

    /**
     * 判断日期是否是节假日
     * @param date 日期
     * @return
     */
    public static boolean checkHoliday(String date) {
        initYear(date.substring(0, 4));
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
        initYear(date.substring(0, 4));
        String key = getKey(CacheTypeEnum.work, date);
        String holiday = RedisUtils.getCacheObject(key);
        return StrUtil.isNotBlank(holiday);
    }

    private static void initYear(String year) {
        initYear(year, true);
    }

    /**
     * 初始化指定年份节假日数据
     * @param year 年份
     * @param nextYear 是否处理下年数据
     */
    private static void initYear(String year, boolean nextYear) {
        String key = getKey(CacheTypeEnum.init, year);
        String holiday = RedisUtils.getCacheObject(key);
        if(StrUtil.isBlank(holiday) && !lock.isLocked()) {
            lock.lock();
            try {
                updateByApi();
                RedisUtils.setCacheObject(key, "Y");
            } catch (Exception e) {
                throw new BizException(ErrCode.ERROR, "同步处理假日数据失败");
            } finally {
                lock.unlock();
            }
        }
        // 本年12月份，处理下一年数据
        if(nextYear && DateUtil.thisMonth() == 11) {
            initYear(DateUtil.nextMonth().toString("yyyy"), false);
        }
    }

    private static void updateByApi() {
        JSON json = JSONUtil.parse(getData());
        List<Holiday> holidays = json.getByPath("showapi_res_body.data", List.class);
        holidays.forEach(holiday -> {
            DateTime begin = DateUtil.parse(holiday.getBegin(), "yyyyMMdd");
            DateTime end = DateUtil.parse(holiday.getEnd(), "yyyyMMdd");
            long days = DateUtil.betweenDay(begin, end, true);
            if(days < 0 || days > 30) {
                throw new IllegalArgumentException();
            }
            for (int i = 0; i <= days; i++) {
                String date = DateUtil.offsetDay(begin, i).toDateStr();
                RedisUtils.setCacheObject(getKey(CacheTypeEnum.holiday, date), "节假日");
            }

            if(CollUtil.isNotEmpty(holiday.getInverse_days())) {
                holiday.getInverse_days().stream()
                        .map(inverseDay -> DateUtil.parse(holiday.getEnd(), "yyyyMMdd").toDateStr())
                        .forEach(inverseDay -> {
                            RedisUtils.setCacheObject(getKey(CacheTypeEnum.work, inverseDay), "调休");
                        });
            }
        });
    }

    private static String getData() {
        String host = "https://jiejiari.market.alicloudapi.com/holidayList";
        String appcode = "26ba8993074a40e78ae6daa533674e1e";
        HttpResponse response = HttpRequest.get(host)
                .header("Authorization", "APPCODE " + appcode)
                .form("year", "2023")
                .execute();

        return response.body();
    }

    private static String getKey(CacheTypeEnum type, Object key) {
        return HOLIDAY_PREFIX + type + ":" + key;
    }

    private enum CacheTypeEnum {
        holiday,
        work,
        init
    }
}
