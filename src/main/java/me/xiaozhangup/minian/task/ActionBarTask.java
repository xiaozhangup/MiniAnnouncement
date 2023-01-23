package me.xiaozhangup.minian.task;

import me.xiaozhangup.minian.ConfigReader;
import me.xiaozhangup.minian.MiniAnnouncement;
import me.xiaozhangup.minian.util.TimeUtil;
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
        // 普通的 Interval Task
        new BukkitRunnable() {
            @Override
            public void run() {
                send();
            }
        }.runTaskTimerAsynchronously(MiniAnnouncement.getInstance(), 0L, ConfigReader.ActionBar.DELAY * 20L);

        // 定时任务
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ConfigReader.ActionBar.TIME.contains(TimeUtil.getNow())) {
                    send();
                }
            }
        }.runTaskTimerAsynchronously(MiniAnnouncement.getInstance(), 0L, 20L);
    }

    private void send() {
        if (ConfigReader.ActionBar.ENABLE) {
            Bukkit.getOnlinePlayers().stream().filter(player -> !ConfigReader.ActionBar.DISABLE_WORLDS.contains(player.getWorld().getName())).forEach(player -> {
                MiniAnnouncement.getAdventure().player(player).sendActionBar(
                        MiniAnnouncement.setPlaceholders(player, ConfigReader.ActionBar.PREFIX + ConfigReader.ActionBar.MESSAGES.get(ThreadLocalRandom.current().nextInt(ConfigReader.ActionBar.MESSAGES.size())))
                );
            });
        }
    }
}
