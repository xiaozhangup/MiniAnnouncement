package me.xiaozhangup.minian.task;

import me.xiaozhangup.minian.ConfigReader;
import me.xiaozhangup.minian.MiniAnnouncement;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.task.ActionBarTask
 *
 * @author xiaomu
 * @since 2022/12/31 4:17 PM
 */
public class ActionBarTask {

    public ActionBarTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ConfigReader.ActionBar.ENABLE) {
                    Bukkit.getOnlinePlayers().stream().filter(player -> !ConfigReader.ActionBar.DISABLE_WORLDS.contains(player.getWorld().getName())).forEach(player -> {
                        MiniAnnouncement.getAdventure().player(player).sendActionBar(
                                MiniAnnouncement.setPlaceholders(player, ConfigReader.ActionBar.PREFIX + ConfigReader.ActionBar.MESSAGES.get(ThreadLocalRandom.current().nextInt(ConfigReader.ActionBar.MESSAGES.size())))
                        );
                    });
                }
            }
        }.runTaskTimerAsynchronously(MiniAnnouncement.getInstance(), 0L, ConfigReader.ActionBar.DELAY * 20L);
    }
}
