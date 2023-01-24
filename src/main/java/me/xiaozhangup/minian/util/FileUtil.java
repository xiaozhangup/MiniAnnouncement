package me.xiaozhangup.minian.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * CustomCooking
 * me.mical.customcooking.util.FileUtil
 *
 * @author xiaomu
 * @since 2022/12/26 9:41 AM
 */
public class FileUtil {

    @NotNull
    public static String getName(@Nullable final File file, @NotNull final String suffix) {
        if (file == null) {
            return "";
        }
        return file.getName().substring(0, file.getName().indexOf(suffix));
    }
}
