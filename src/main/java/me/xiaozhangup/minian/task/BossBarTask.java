package me.xiaozhangup.minian.task;

import me.xiaozhangup.minian.ConfigReader;
import me.xiaozhangup.minian.MiniAnnouncement;
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
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ConfigReader.BossBar.ENABLE) {
                    Bukkit.getOnlinePlayers().stream().filter(player -> !ConfigReader.BossBar.DISABLE_WORLDS.contains(player.getWorld().getName())).forEach(player -> {
                        final BossBar bossBar = BossBar.bossBar(
                                ConfigReader.BossBar.PREFIX.append(ConfigReader.BossBar.MESSAGES.get(ThreadLocalRandom.current().nextInt(ConfigReader.BossBar.MESSAGES.size()))),
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
        }.runTaskTimerAsynchronously(MiniAnnouncement.getInstance(), 0L, ConfigReader.BossBar.DELAY * 20L);
    }
}
