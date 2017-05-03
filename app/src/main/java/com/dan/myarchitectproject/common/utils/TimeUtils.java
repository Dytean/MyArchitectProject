package com.dan.myarchitectproject.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Dan
 * Date: 2017/4/10 上午10:23
 * Description:
 * PackageName: com.dan.myarchitectproject.common.utils
 * Copyright: 杭州安存网络科技有限公司
 **/

public class TimeUtils {
    private static String PATTERN_FORMART = "yyyy-MM-ddHH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YY_MM_DD_HH_MM = "yy-MM-dd HH:mm";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String JZ_YY_MM_DD_HH_MM_SS = "yy/MM/dd HH:mm:ss";

    public static final String MM_DD = "MM月dd日";

    public static final String JZ_MM_DD_HH_MM = "yy/MM/dd HH:mm";

    /**
     * 格式化时间
     *
     * @param timeL
     * @return
     */
    public static String formatDisplayTime(String timeL) {
        if (StringUtils.isEmpty(timeL)) {
            return "";
        }
        String time = getDateYHDHMS(Long.parseLong(timeL), YYYY_MM_DD_HH_MM_SS);
        String display = "";
        int tMin = 60 * 1000;
        int tHour = 60 * tMin;
        int tDay = 24 * tHour;

        if (time != null) {
            try {
                Date tDate = new SimpleDateFormat(PATTERN_FORMART).parse(time);
                Date today = new Date();
                SimpleDateFormat thisYearDf = new SimpleDateFormat("yyyy");
                SimpleDateFormat todayDf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat todayTime = new SimpleDateFormat("HH:mm");
                Date thisYear = new Date(thisYearDf.parse(thisYearDf.format(today)).getTime());
                Date yesterday = new Date(todayDf.parse(todayDf.format(today)).getTime());
                Date beforeYes = new Date(yesterday.getTime() - tDay);
                if (tDate != null) {
                    SimpleDateFormat halfDf = new SimpleDateFormat("MM月dd日");
                    long dTime = today.getTime() - tDate.getTime();
                    if (tDate.before(thisYear)) {
                        display = new SimpleDateFormat("yyyy年").format(tDate);
                    } else {
                        if (dTime < tMin) {
                            display = "刚刚";
                        } else if (dTime < tHour) {
                            display = (int) (dTime / tMin) + " 分钟前";
                        } else if (dTime < tDay && tDate.after(yesterday)) {
                            display = "今天   " + todayTime.format(tDate);
                        } else if (tDate.after(beforeYes) && tDate.before(yesterday)) {
                            display = "昨天  " + new SimpleDateFormat("HH:mm").format(tDate);
                        } else {
                            display = halfDf.format(tDate);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return display;
    }

    public static String getDateYHDHMS(long time, String formatType) {
        String retStr = "";
        SimpleDateFormat sf = new SimpleDateFormat(formatType);
        if (0 != time) {
            Date date = new Date(time);
            retStr = sf.format(date);
            return retStr;
        }
        return retStr;
    }

    /**
     * 秒转成时间格式 如 100s - > 00:01:40
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        StringBuffer timeStr = new StringBuffer();
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                // timeStr = unitFormat(minute) + "分" + unitFormat(second)+"秒";
                if (minute != 0) {
                    if (second == 0) {
                        if (minute == 1)
                            timeStr.append("00:" + "00:" + time);
                        else
                            timeStr.append("00:" + unitFormat(minute) + ":00");
                    } else {
                        timeStr.append("00:" + unitFormat(minute) + ":");
                    }

                }
                if (second != 0) {
                    if (0 == minute)
                        timeStr.append("00:" + "00:" + unitFormat(second));
                    else
                        timeStr.append("00:" + unitFormat(second) + "");
                }
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                // timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分"
                // + unitFormat(second)+"秒";
                if (hour != 0) {
                    timeStr.append(unitFormat(hour) + ":");
                }
                if (minute != 0) {
                    timeStr.append(unitFormat(minute) + ":");
                } else if (minute == 0 && second != 0) {
                    timeStr.append("00:");
                }

                if (second == 0) {
                    timeStr.append("00");
                }

                if (second != 0) {
                    timeStr.append(unitFormat(second) + "");
                }
            }
        }
        return timeStr.toString();
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

}
