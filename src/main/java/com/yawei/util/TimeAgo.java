package com.yawei.util;

import org.joda.time.DateTime;

public class TimeAgo {
    /**
     * 将时间转换为相对时间
     * @param time 时间字符串 exp : 2016-06-03 15:23:26
     * @return 相对时间
     * 1.一年之前的输出格式化时间
     * 2.月份输出范围： 11 个月前-2个月前 以及上个月
     * 3.天数输出范围：半个月前/一周前/6天前-3天前/前天/昨天
     * 4.小时输出范围：23小时-1小时前/半小时前
     * 5.分钟输出范围：29分钟前-1分钟前以及刚刚
     */
    public static String transTime(String time) {

        String[] array = time.split("[- :]");
        int[] arr = new int[7];
        for (int i = 0; i < array.length; i++) {
            arr[i] = Integer.parseInt(array[i]);
        }
        DateTime obj = new DateTime(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6]);
        if (obj.plusMonths(1).isBeforeNow() && obj.plusMonths(12).isAfterNow()) {
            for (int i = 1; i <= 12; i++) {
                if (obj.plusMonths(i).isAfterNow()) {
                    return i> 1 ? i + "个月前":"上个月";
                }
            }
        } else if (obj.plusDays(15).isBeforeNow() && obj.plusDays(31).isAfterNow()) {
            return "半月前";
        } else if (obj.plusDays(7).isBeforeNow() && obj.plusDays(15).isAfterNow()) {
            return "一周前";
        } else if (obj.plusDays(1).isBeforeNow() && obj.plusDays(7).isAfterNow()) {
            for (int i = 1; i <= 7; i++) {
                if (obj.plusDays(i).isAfterNow()) {
                    return i > 2 ? i + "天前" : (i > 1 ? "前天" : "昨天");
                }
            }
        } else if (obj.plusHours(1).isBeforeNow() && obj.plusHours(24).isAfterNow()) {
            for (int i = 1; i <= 24; i++) {
                if (obj.plusHours(i).isAfterNow()) {
                    return i + "小时前";
                }
            }
        } else if (obj.plusMinutes(30).isBeforeNow() && obj.plusMinutes(60).isAfterNow()) {
            return "半小时前";

        } else if (obj.plusMinutes(1).isBeforeNow() && obj.plusMinutes(30).isAfterNow()) {
            for (int i = 1; i <= 30; i++) {
                if (obj.plusMinutes(i).isAfterNow()) {
                    return i + "分钟前";
                }
            }
        } else if (obj.plusMinutes(1).isAfterNow()){
            return "刚刚";
        }
        return time;
    }
}
