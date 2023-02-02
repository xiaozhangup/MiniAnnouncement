package me.xiaozhangup.minian.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.util.TimeUtil
 *
 * @author xiaomu
 * @since 2023/1/24 12:54 AM
 */
public class TimeUtil {

    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    public static String getNow() {
        return format.format(new Date());
    }
}
