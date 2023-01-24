package me.xiaozhangup.minian.util;

import me.xiaozhangup.minian.MiniAnnouncement;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.util.ConfigUtil
 *
 * @author xiaomu
 * @since 2023/1/24 3:25 PM
 */
public class ConfigUtil {

    public static YamlConfiguration getConfig(final String name) {
        final File file = new File(MiniAnnouncement.getInstance().getDataFolder(), name);
        if (!file.exists()) {
            MiniAnnouncement.getInstance().saveResource(name, true);
            LocaleUtil.sendToConsole("已自动生成并准备加载配置文件 {0}.", name);
            return YamlConfiguration.loadConfiguration(file);
        }
        LocaleUtil.sendToConsole("已加载配置文件 {0}.", name);
        return YamlConfiguration.loadConfiguration(file);
    }

    public static List<File> getData(final String name, @Nullable final Consumer<JavaPlugin> saveDefault) {
        final File folder = new File(MiniAnnouncement.getInstance().getDataFolder(), name);
        final List<File> result = new ArrayList<>();
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                if (saveDefault != null) {
                    saveDefault.accept(MiniAnnouncement.getInstance());
                }
                LocaleUtil.sendToConsole("未找到数据文件夹 {0}, 已自动生成.", name);
            } else {
                LocaleUtil.sendToConsole("自动生成数据文件夹 {0} 失败.", name);
            }
            return result;
        }
        final File[] files = folder.listFiles(file -> file.getName().endsWith(".yml"));
        if (files != null) {
            Collections.addAll(result, files);
        }
        return result;
    }
}
