package me.xiaozhangup.minian;

import me.xiaozhangup.minian.util.ConfigUtil;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.ConfigReader
 *
 * @author xiaomu
 * @since 2023/1/24 4:07 PM
 */
public class ConfigReader {

    public static boolean B_STATS;

    public static void loadConfig() {
        final YamlConfiguration config = ConfigUtil.getConfig("config.yml");
        B_STATS = config.getBoolean("bStats", true);
    }
}
