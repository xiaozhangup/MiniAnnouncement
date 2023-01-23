package me.xiaozhangup.minian.task;

import me.xiaozhangup.minian.ConfigReader;
import me.xiaozhangup.minian.MiniAnnouncement;
import me.xiaozhangup.minian.util.TimeUtil;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.task.BossBarTask
 *
 * @author xiaomu
 * @since 2022/12/31 4:16 PM
 */
public class BossBarTask {

    public BossBarTask() {
        // 普通的 Interval Task
        new BukkitRunnable() {
            @Override
            public void run() {
                send();
            }
        }.runTaskTimerAsynchronously(MiniAnnouncement.getInstance(), 0L, ConfigReader.BossBar.DELAY * 20L);

        // 定时任务
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ConfigReader.BossBar.TIME.contains(TimeUtil.getNow())) {
                    send();
                }
            }
        }.runTaskTimerAsynchronously(MiniAnnouncement.getInstance(), 0L, 20L);
    }

    private void send() {
        if (ConfigReader.BossBar.ENABLE) {
            Bukkit.getOnlinePlayers().stream().filter(player -> !ConfigReader.BossBar.DISABLE_WORLDS.contains(player.getWorld().getName())).forEach(player -> {
                final BossBar bossBar = BossBar.bossBar(
                        MiniAnnouncement.setPlaceholders(player, ConfigReader.BossBar.PREFIX + ConfigReader.BossBar.MESSAGES.get(ThreadLocalRandom.current().nextInt(ConfigReader.BossBar.MESSAGES.size()))),
                        1,
                        BossBar.Color.valueOf(ConfigReader.BossBar.COLOR),
                        BossBar.Overlay.NOTCHED_20
                );
                MiniAnnouncement.getAdventure().player(player).showBossBar(bossBar);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        MiniAnnouncement.getAdventure().player(player).hideBossBar(bossBar);
                    }
                }.runTaskLaterAsynchronously(MiniAnnouncement.getInstance(), ConfigReader.BossBar.STAY * 20L);
            });
        }
    }
}
