package me.xiaozhangup.minian.migrate;

import me.xiaozhangup.minian.MiniAnnouncement;
import me.xiaozhangup.minian.config.ActionBarConfig;
import me.xiaozhangup.minian.config.BossBarConfig;
import me.xiaozhangup.minian.config.MessageConfig;
import me.xiaozhangup.minian.config.TitleConfig;
import me.xiaozhangup.minian.object.ActionBarMessage;
import me.xiaozhangup.minian.object.BossBarMessage;
import me.xiaozhangup.minian.object.Message;
import me.xiaozhangup.minian.object.TitleMessage;
import me.xiaozhangup.minian.object.struct.BossBar;
import me.xiaozhangup.minian.object.struct.Title;
import me.xiaozhangup.minian.util.ConfigUtil;
import me.xiaozhangup.minian.util.LocaleUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.migrate.ConfigMigrator
 *
 * @author xiaomu
 * @since 2023/1/24 3:31 PM
 */
public class ConfigMigrator {

    public static void migrate(final CommandSender sender) {
        LocaleUtil.sendTo(sender, "开始迁移...");
        migrateActionBar(sender);
        migrateBossBar(sender);
        migrateMessage(sender);
        migrateTitle(sender);
        LocaleUtil.sendTo(sender, "成功迁移! 请重启服务器或重载插件来应用!");
    }

    private static void migrateActionBar(final CommandSender sender) {
        LocaleUtil.sendTo(sender, "尝试迁移 actionbars.yml...");
        try {
            final YamlConfiguration config = ConfigUtil.getConfig("actionbars.yml");
            final YamlConfiguration newConfig = new YamlConfiguration();
            final File folder = new File(MiniAnnouncement.getInstance().getDataFolder(), "actionbars");
            final File file = new File(folder, "old.yml");
            if (!file.exists()) {
                file.createNewFile();
            }
            final long delay = config.getLong("delay");
            final String prefix = config.getString("prefix", "");
            final List<String> messages = config.getStringList("messages");
            final List<String> disableWorlds = config.getStringList("disableWorlds");
            final List<String> time = config.getStringList("time");
            final ActionBarMessage message = new ActionBarMessage(messages, disableWorlds, delay, prefix, time);
            newConfig.set("delay", delay);
            newConfig.set("messages", message);
            newConfig.set("disableWorlds", disableWorlds);
            newConfig.set("time", time);
            newConfig.set("prefix", prefix);
            newConfig.save(file);
            ActionBarConfig.DATA.put("old", message);
            LocaleUtil.sendTo(sender, "成功迁移 actionbars.yml!");
        } catch (Throwable ex) {
            LocaleUtil.sendTo(sender, "尝试迁移 actionbars.yml 时遇到错误, 请检查控制台!");
            LocaleUtil.sendToConsole("迁移 动作栏定时公告 时遇到错误({0}).", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void migrateBossBar(final CommandSender sender) {
        LocaleUtil.sendTo(sender, "尝试迁移 bossbars.yml...");
        try {
            final YamlConfiguration config = ConfigUtil.getConfig("bossbars.yml");
            final YamlConfiguration newConfig = new YamlConfiguration();
            final File folder = new File(MiniAnnouncement.getInstance().getDataFolder(), "bossbars");
            final File file = new File(folder, "old.yml");
            if (!file.exists()) {
                file.createNewFile();
            }
            final long delay = config.getLong("delay");
            final long stay = config.getLong("stay");
            final String prefix = config.getString("prefix", "");
            final String color = config.getString("color", "");
            final List<String> messages = config.getStringList("messages");
            final List<String> disableWorlds = config.getStringList("disableWorlds");
            final List<String> time = config.getStringList("time");
            final List<Map<?, ?>> mapList = new ArrayList<>();
            final List<BossBar> msgs = new ArrayList<>();
            for (String msg : messages) {
                final Map<String, Object> map = new HashMap<>();
                map.put("color", color);
                map.put("stay", stay);
                map.put("message", msg);
                mapList.add(map);
                final BossBar bossBar = new BossBar(color, stay, msg);
                msgs.add(bossBar);
            }
            final BossBarMessage message = new BossBarMessage(msgs, disableWorlds, delay, prefix, time);
            newConfig.set("delay", delay);
            newConfig.set("prefix", prefix);
            newConfig.set("messages", mapList);
            newConfig.set("disableWorlds", disableWorlds);
            newConfig.set("time", time);
            newConfig.save(file);
            BossBarConfig.DATA.put("old", message);
        } catch (Throwable ex) {
            LocaleUtil.sendTo(sender, "尝试迁移 bossbars.yml 时遇到错误, 请检查控制台!");
            LocaleUtil.sendToConsole("迁移 BossBar 定时公告 时遇到错误({0}).", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void migrateMessage(final CommandSender sender) {
        LocaleUtil.sendTo(sender, "尝试迁移 messages.yml...");
        try {
            final YamlConfiguration config = ConfigUtil.getConfig("messages.yml");
            final YamlConfiguration newConfig = new YamlConfiguration();
            final File folder = new File(MiniAnnouncement.getInstance().getDataFolder(), "messages");
            final File file = new File(folder, "old.yml");
            if (!file.exists()) {
                file.createNewFile();
            }
            final long delay = config.getLong("delay");
            final String prefix = config.getString("prefix", "");
            final List<String> messages = config.getStringList("messages");
            final List<String> disableWorlds = config.getStringList("disableWorlds");
            final List<String> time = config.getStringList("time");
            final Message message = new Message(messages, disableWorlds, delay, prefix, time);
            newConfig.set("delay", delay);
            newConfig.set("prefix", prefix);
            newConfig.set("messages", message);
            newConfig.set("disableWorlds", disableWorlds);
            newConfig.set("time", time);
            MessageConfig.DATA.put("old", message);
        } catch (Throwable ex) {
            LocaleUtil.sendTo(sender, "尝试迁移 messages.yml 时遇到错误, 请检查控制台!");
            LocaleUtil.sendToConsole("迁移 信息定时公告 时遇到错误({0}).", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void migrateTitle(final CommandSender sender) {
        LocaleUtil.sendTo(sender, "尝试迁移 titles.yml...");
        try {
            final YamlConfiguration config = ConfigUtil.getConfig("titles.yml");
            final YamlConfiguration newConfig = new YamlConfiguration();
            final File folder = new File(MiniAnnouncement.getInstance().getDataFolder(), "titles");
            final File file = new File(folder, "old.yml");
            if (!file.exists()) {
                file.createNewFile();
            }
            final long delay = config.getLong("delay");
            final long fadeIn = config.getLong("fadeIn");
            final long stayOnScreen = config.getLong("stayOnScreen");
            final long fadeOut = config.getLong("fadeOut");
            final String prefix = config.getString("prefix", "");
            final Map<String, String> messages = new HashMap<>();
            for (Map<?, ?> map : config.getMapList("messages")) {
                String title = "";
                String subtitle = "";
                if (map.containsKey("title")) {
                    title = (String) map.get("title");
                }
                if (map.containsKey("subtitle")) {
                    subtitle = (String) map.get("subtitle");
                }
                messages.put(title, subtitle);
            }
            final List<String> disableWorlds = config.getStringList("disableWorlds");
            final List<String> time = config.getStringList("time");
            final List<Map<?, ?>> mapList = new ArrayList<>();
            final List<Title> msgs = new ArrayList<>();
            for (Map.Entry<String, String> entry : messages.entrySet()) {
                final Map<String, Object> map = new HashMap<>();
                map.put("title", entry.getKey());
                map.put("subtitle", entry.getValue());
                map.put("fadeIn", fadeIn);
                map.put("stayOnScreen", stayOnScreen);
                map.put("fadeOut", fadeOut);
                mapList.add(map);
                final Title title = new Title(entry.getKey(), entry.getValue(), fadeIn, stayOnScreen, fadeOut);
                msgs.add(title);
            }
            final TitleMessage message = new TitleMessage(msgs, disableWorlds, delay, prefix, time);
            newConfig.set("delay", delay);
            newConfig.set("prefix", prefix);
            newConfig.set("messages", mapList);
            newConfig.set("disableWorlds", disableWorlds);
            newConfig.set("time", time);
            newConfig.save(file);
            TitleConfig.DATA.put("old", message);
        } catch (Throwable ex) {
            LocaleUtil.sendTo(sender, "尝试迁移 titles.yml 时遇到错误, 请检查控制台!");
            LocaleUtil.sendToConsole("迁移 标题定时公告 时遇到错误({0}).", ex.getMessage());
            ex.printStackTrace();
        }
    }
}
