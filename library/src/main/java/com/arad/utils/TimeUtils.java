/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arad.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    public static String getTimeAgo(long time) {
        // TODO: use DateUtils methods instead
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = AndroidUtil.getCurrentTime();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE) {
            return "刚刚";
        } else if (diff < 2 * MINUTE) {
            return "1分钟前";
        } else if (diff < 50 * MINUTE) {
            return diff / MINUTE + " 分钟前";
        } else if (diff < 90 * MINUTE) {
            return "1小时前";
        } else if (diff < 24 * HOUR) {
            return diff / HOUR + " 小时前";
        } else if (diff < 48 * HOUR) {
            return "昨天";
        } else {
            return diff / DAY + " 天之前";
        }
    }

    /**
     * 字符串转Date
     *
     * @param user_time yyyy年MM月dd日HH时mm分ss秒
     * @return Date
     */
    public static Date getTime(String user_time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        Date date = null;
        try {
            date = sdf.parse(user_time);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }

    public static String countDownTime(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = AndroidUtil.getCurrentTime();
        if (time <= now || time <= 0) {
            return null;
        }

        final long diff = time - now;
        long day = diff / DAY;
        long hours = diff % DAY / HOUR;
        long minute = diff % HOUR / MINUTE;
        long second = diff % MINUTE / SECOND;

        return day + "天" + hours + "时" + minute + "分" + second + "秒";
    }

}
