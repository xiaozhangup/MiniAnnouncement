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

    public static String getNow() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
}
