package me.xiaozhangup.minian.config;

import me.xiaozhangup.minian.object.TitleMessage;
import me.xiaozhangup.minian.object.struct.Title;
import me.xiaozhangup.minian.util.ConfigUtil;
import me.xiaozhangup.minian.util.FileUtil;
import me.xiaozhangup.minian.util.LocaleUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.config.TitleConfig
 *
 * @author xiaomu
 * @since 2023/1/24 3:25 PM
 */
public class TitleConfig {

    public static final Map<String, TitleMessage> DATA = new HashMap<>();
    public static final List<String> STOPPED = new ArrayList<>();

    public static void loadTitles() {
        DATA.clear();
        final List<File> files = ConfigUtil.getData("titles", plugin -> plugin.saveResource("actionbars/Example.yml", true));
        for (File file : files) {
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            final String id = FileUtil.getName(file, ".yml");
            final long delay = config.getLong("delay");
            final String prefix = config.getString("prefix", "");
            final List<Title> messages = new ArrayList<>();
            for (Map<?, ?> map : config.getMapList("messages")) {
                String title = "";
                String subtitle = "";
                long fadeIn = 0L;
                long stayOnScreen = 0L;
                long fadeOut = 0L;
                if (map.containsKey("title")) {
                    title = (String) map.get("title");
                }
                if (map.containsKey("subtitle")) {
                    subtitle = (String) map.get("subtitle");
                }
                if (map.containsKey("fadeIn")) {
                    fadeIn = (long) ((Object) map.get("fadeIn"));
                }
                if (map.containsKey("stayOnScreen")) {
                    stayOnScreen = (long) ((Object) map.get("stayOnScreen"));
                }
                if (map.containsKey("fadeOut")) {
                    fadeOut = (long) ((Object) map.get("fadeOut"));
                }
                messages.add(new Title(title, subtitle, fadeIn, stayOnScreen, fadeOut));
            }
            final List<String> disableWorlds = config.getStringList("disableWorlds");
            final List<String> time = config.getStringList("time");
            final TitleMessage message = new TitleMessage(messages, disableWorlds, delay, prefix, time);
            DATA.putIfAbsent(id, message);
        }
        LocaleUtil.sendToConsole("加载了 {0} 个标题定时公告.", DATA.size());
    }
}
