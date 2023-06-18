package com.hospital.attendance.utils;

/**
 * 距离工具类
 * @author liguoxian
 */
public class DistanceUtils {

    /**
     * 判断两个位置的距离是否超过指定范围
     * @param lat 经度
     * @param lng 维度
     * @param centerLat 距离经度
     * @param centerLng 距离维度
     * @param radius 范围：米
     * @return
     */
    public static boolean isInCircle(double lat, double lng,
                                     double centerLat, double centerLng, double radius) {
        double distance = getDistance(lat, lng, centerLat, centerLng);
        return distance <= radius;
    }

    /**
     * 获取两个位置的距离
     * @param lat1 经度
     * @param lng1 维度
     * @param lat2 距离经度
     * @param lng2 距离维度
     * @return 单位：米
     */
    private static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        // 地球半径，单位：米
        double earthRadius = 6378137;
        double x = Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                Math.cos((lng2 - lng1) * Math.PI / 180);
        double y = Math.sin(lat1 * Math.PI / 180) * Math.sin(lat2 * Math.PI / 180);
        double s = x + y;
        if (s > 1) {
            s = 1;
        }
        if (s < -1) {
            s = -1;
        }
        double alpha = Math.acos(s);
        return alpha * earthRadius;
    }
}
