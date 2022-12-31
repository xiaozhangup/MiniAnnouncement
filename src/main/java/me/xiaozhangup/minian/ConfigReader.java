package me.xiaozhangup.minian;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MiniAnnouncements
 * me.xiaozhangup.minian.ConfigReader
 *
 * @author xiaomu
 * @since 2022/12/31 4:02 PM
 */
public class ConfigReader {

    public static YamlConfiguration getConfig(final String name) {
        final File file = new File(MiniAnnouncement.getInstance().getDataFolder(), name);
        if (!file.exists()) {
            MiniAnnouncement.getInstance().saveResource(name, true);
            MiniAnnouncement.getInstance().getLogger().info("已自动生成并准备加载配置文件 " + name);
            return YamlConfiguration.loadConfiguration(file);
        }
        MiniAnnouncement.getInstance().getLogger().info("已加载配置文件 " + name);
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void loadConfig() {
        ActionBar.loadActionBars();
        BossBar.loadBossBars();
        Message.loadMessages();
        Title.loadTitles();
    }

    public static class ActionBar {

        public static boolean ENABLE;
        public static long DELAY;
        public static Component PREFIX;
        public static List<Component> MESSAGES;
        public static List<String> DISABLE_WORLDS;

        public static void loadActionBars() {
            final YamlConfiguration config = getConfig("actionbars.yml");
            ENABLE = config.getBoolean("enable");
            DELAY = config.getLong("delay");
            PREFIX = MiniAnnouncement.getMiniMessage().deserialize(config.getString("prefix", ""));
            MESSAGES = config.getStringList("messages").stream().map(MiniAnnouncement.getMiniMessage()::deserialize).collect(Collectors.toList());
            DISABLE_WORLDS = config.getStringList("disableWorlds");
        }
    }

    public static class BossBar {

        public static boolean ENABLE;
        public static long DELAY;
        public static long STAY;
        public static String COLOR;
        public static Component PREFIX;
        public static List<Component> MESSAGES;
        public static List<String> DISABLE_WORLDS;

        public static void loadBossBars() {
            final YamlConfiguration config = getConfig("bossbars.yml");
            ENABLE = config.getBoolean("enable");
            DELAY = config.getLong("delay");
            STAY = config.getLong("stay");
            COLOR = config.getString("color");
            PREFIX = MiniAnnouncement.getMiniMessage().deserialize(config.getString("prefix", ""));
            MESSAGES = config.getStringList("messages").stream().map(MiniAnnouncement.getMiniMessage()::deserialize).collect(Collectors.toList());
            DISABLE_WORLDS = config.getStringList("disableWorlds");
        }
    }

    public static class Message {

        public static boolean ENABLE;
        public static long DELAY;
        public static Component PREFIX;
        public static List<Component> MESSAGES;
        public static List<String> DISABLE_WORLDS;

        public static void loadMessages() {
            final YamlConfiguration config = getConfig("messages.yml");
            ENABLE = config.getBoolean("enable");
            DELAY = config.getLong("delay");
            PREFIX = MiniAnnouncement.getMiniMessage().deserialize(config.getString("prefix", ""));
            MESSAGES = config.getStringList("messages").stream().map(MiniAnnouncement.getMiniMessage()::deserialize).collect(Collectors.toList());
            DISABLE_WORLDS = config.getStringList("disableWorlds");
        }
    }

    public static class Title {

        public static boolean ENABLE;
        public static long DELAY;
        public static long FADE_IN;
        public static long STAY_ON_SCREEN;
        public static long FADE_OUT;
        public static Component PREFIX;
        public static Map<Component, Component> MESSAGES = new HashMap<>();
        public static List<String> DISABLE_WORLDS;

        public static void loadTitles() {
            final YamlConfiguration config = getConfig("titles.yml");
            ENABLE = config.getBoolean("enable");
            DELAY = config.getLong("delay");
            FADE_IN = config.getLong("fadeIn");
            STAY_ON_SCREEN = config.getLong("stayOnScreen");
            FADE_OUT = config.getLong("fadeOut");
            PREFIX = MiniAnnouncement.getMiniMessage().deserialize(config.getString("prefix", ""));
            MESSAGES.clear();
            for (Map<?, ?> map : config.getMapList("messages")) {
                if (!map.containsKey("title") || !map.containsKey("subtitle")) {
                    continue;
                }
                final Component title = MiniAnnouncement.getMiniMessage().deserialize((String) map.get("title"));
                final Component subtitle = MiniAnnouncement.getMiniMessage().deserialize((String) map.get("subtitle"));
                MESSAGES.put(title, subtitle);
            }
            DISABLE_WORLDS = config.getStringList("disableWorlds");
        }
    }
}
