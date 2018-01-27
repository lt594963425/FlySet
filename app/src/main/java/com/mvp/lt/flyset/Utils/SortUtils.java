package com.mvp.lt.flyset.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * $name
 *
 * @author ${LiuTao}
 * @date 2017/12/22/022
 */

public class SortUtils {

    public static List<String> sortByStatus() {
        List<String> list = new ArrayList<String>();
        list.add("2017-12-24 14.52.33");
        list.add("2017-12-22 14.52.33");
        list.add("2017-12-25 14.52.33");
        list.add("2017-12-26 14.52.33");
        list.add("2017-12-26 14.52.33");
        list.add("2017-12-23 14.52.33");
        list.add("2017-12-28 14.52.33");
        list.add("2017-12-29 14.52.33");
        list.add("2017-12-21 11.52.33");

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s2.compareTo(s1);
            }
        });
        for (String string : list) {
            System.out.println(string);
        }
        return list;
    }

    /**
     * 已知一点经纬度A，和与另一点B的距离和方位角，求B的经纬度
     *
     * @param lng1
     *            A的经度
     * @param lat1
     *            A的纬度
     * @param distance
     *            AB距离（单位：米）
     * @param azimuth
     *            AB方位角
     * @return B的经纬度
     * */
//    public static LatLonPoint convertDistanceToLogLat(double lng1, double lat1,
//                                                      double distance, double azimuth) {
//
//        azimuth = rad(azimuth);
//        // 将距离转换成经度的计算公式
//        double lon = lng1 + (distance * Math.sin(azimuth))/ (EARTH_ARC * Math.cos(rad(lat1)));
//        // 将距离转换成纬度的计算公式
//        double lat = lat1 + (distance * Math.cos(azimuth)) / EARTH_ARC;
//
//        LatLonPoint latLonPoint = new LatLonPoint(lat, lon);
////        Logger.info("convertDistanceToLogLat=" + lat + "   lon=" + lon);
//        return latLonPoint;
//    }
//    /**
//     * 转化为弧度(rad)
//     * */
//    public static double rad(double d) {
//        return d * Math.PI / 180.0;
//    }
//    /**
//     * 地球每度的弧长(km)
//     * */
//    public final static double EARTH_ARC = 111.199;
}
