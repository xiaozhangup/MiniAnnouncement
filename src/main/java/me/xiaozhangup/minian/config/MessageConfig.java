package me.xiaozhangup.minian.config;

import me.xiaozhangup.minian.object.Message;
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
 * me.xiaozhangup.minian.config.MessageConfig
 *
 * @author xiaomu
 * @since 2023/1/24 3:24 PM
 */
public class MessageConfig {

    public static final Map<String, Message> DATA = new HashMap<>();
    public static final List<String> STOPPED = new ArrayList<>();

    public static void loadMessages() {
        DATA.clear();
        final List<File> files = ConfigUtil.getData("messages", plugin -> plugin.saveResource("actionbars/Example.yml", true));
        for (File file : files) {
            final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            final String id = FileUtil.getName(file, ".yml");
            final long delay = config.getLong("delay");
            final String prefix = config.getString("prefix", "");
            final List<String> messages = config.getStringList("messages");
            final List<String> disableWorlds = config.getStringList("disableWorlds");
            final List<String> time = config.getStringList("time");
            final Message message = new Message(messages, disableWorlds, delay, prefix, time);
            DATA.putIfAbsent(id, message);
        }
        LocaleUtil.sendToConsole("加载了 {0} 个信息定时公告.", DATA.size());
    }
}
