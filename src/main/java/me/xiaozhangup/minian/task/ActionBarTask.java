package me.xiaozhangup.minian.task;

import me.xiaozhangup.minian.MiniAnnouncement;
import me.xiaozhangup.minian.config.ActionBarConfig;
import me.xiaozhangup.minian.object.ActionBarMessage;
import me.xiaozhangup.minian.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
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
        for (Map.Entry<String, ActionBarMessage> entry : ActionBarConfig.DATA.entrySet()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    send(entry.getKey(), entry.getValue());
                }
            }.runTaskTimerAsynchronously(MiniAnnouncement.getInstance(), 0L, entry.getValue().getDelay() * 20L);

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

    private void send(final String id, final ActionBarMessage message) {
        if (ActionBarConfig.STOPPED.contains(id)) return;
        Bukkit.getOnlinePlayers().stream().filter(player -> !message.getDisableWorlds().contains(player.getWorld().getName())).forEach(player -> {
            MiniAnnouncement.getAdventure().player(player).sendActionBar(
                    MiniAnnouncement.setPlaceholders(player, message.getPrefix() + message.getMessages().get(ThreadLocalRandom.current().nextInt(message.getMessages().size())))
            );
        });
    }
}
