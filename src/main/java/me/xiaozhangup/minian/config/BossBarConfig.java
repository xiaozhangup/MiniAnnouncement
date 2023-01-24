package me.xiaozhangup.minian.config;

import me.xiaozhangup.minian.object.BossBarMessage;
import me.xiaozhangup.minian.object.struct.BossBar;
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
 * me.xiaozhangup.minian.config.BossBarConfig
 *
 * @author xiaomu
 * @since 2023/1/24 3:24 PM
 */
public class BossBarConfig {

    public static final Map<String, BossBarMessage> DATA = new HashMap<>();
    public static final List<String> STOPPED = new ArrayList<>();

    public static void loadBossBars() {
        DATA.clear();
        final List<File> files = ConfigUtil.getData("bossbars", plugin -> plugin.saveResource("actionbars/Example.yml", true));
        for (File file : files) {
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            final String id = FileUtil.getName(file, ".yml");
            final long delay = config.getLong("delay");
            final List<BossBar> messages = new ArrayList<>();
            for (Map<?, ?> map : config.getMapList("messages")) {
                final String message = (String) map.get("message");
                final String color = (String) map.get("color");
                final long stay = Long.parseLong(map.get("stay").toString());
                messages.add(new BossBar(color, stay, message));
            }
            final String prefix = config.getString("prefix", "");
            final List<String> disableWorlds = config.getStringList("disableWorlds");
            final List<String> time = config.getStringList("time");
            final BossBarMessage message = new BossBarMessage(messages, disableWorlds, delay, prefix, time);
            DATA.putIfAbsent(id, message);
        }
        LocaleUtil.sendToConsole("加载了 {0} 个 BossBar 定时公告.", DATA.size());
    }
}
