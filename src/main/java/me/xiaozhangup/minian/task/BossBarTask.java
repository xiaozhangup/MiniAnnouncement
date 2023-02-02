package me.xiaozhangup.minian.task;

import me.xiaozhangup.minian.MiniAnnouncement;
import me.xiaozhangup.minian.config.BossBarConfig;
import me.xiaozhangup.minian.object.BossBarMessage;
import me.xiaozhangup.minian.util.TimeUtil;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
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
        for (Map.Entry<String, BossBarMessage> entry : BossBarConfig.DATA.entrySet()) {
            // 普通的 Interval Task
            if (entry.getValue().getDelay() != -1) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        send(entry.getKey(), entry.getValue());
                    }
                }.runTaskTimerAsynchronously(MiniAnnouncement.getInstance(), 0L, entry.getValue().getDelay() * 20L);
            }

            // 定时任务
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (entry.getValue().getTime().contains(TimeUtil.getNow())) {
                        send(entry.getKey(), entry.getValue());
                    }
                }
            }.runTaskTimerAsynchronously(MiniAnnouncement.getInstance(), 0L, 20L);
        }
    }

    private void send(final String id, final BossBarMessage message) {
        if (BossBarConfig.STOPPED.contains(id)) return;
        Bukkit.getOnlinePlayers().stream().filter(player -> !message.getDisableWorlds().contains(player.getWorld().getName())).forEach(player -> {
            final me.xiaozhangup.minian.object.struct.BossBar msg = message.getMessages().get(ThreadLocalRandom.current().nextInt(message.getMessages().size()));
            final BossBar bossBar = BossBar.bossBar(
                    MiniAnnouncement.setPlaceholders(player, message.getPrefix() + msg.getMessage()),
                    1,
                    BossBar.Color.valueOf(msg.getColor()),
                    BossBar.Overlay.NOTCHED_20
            );
            MiniAnnouncement.getAdventure().player(player).showBossBar(bossBar);
            new BukkitRunnable() {
                @Override
                public void run() {
                    MiniAnnouncement.getAdventure().player(player).hideBossBar(bossBar);
                }
            }.runTaskLaterAsynchronously(MiniAnnouncement.getInstance(), msg.getStay() * 20L);
        });
    }
}
