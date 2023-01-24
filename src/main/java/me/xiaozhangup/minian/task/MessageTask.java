package me.xiaozhangup.minian.task;

import me.xiaozhangup.minian.MiniAnnouncement;
import me.xiaozhangup.minian.config.MessageConfig;
import me.xiaozhangup.minian.object.Message;
import me.xiaozhangup.minian.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.task.MessageTask
 *
 * @author xiaomu
 * @since 2022/12/31 4:16 PM
 */
public class MessageTask {

    public MessageTask() {
        for (Map.Entry<String, Message> entry : MessageConfig.DATA.entrySet()) {
            // 普通的 Interval Task
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

    private void send(final String id, final Message message) {
        if (MessageConfig.STOPPED.contains(id)) return;
        Bukkit.getOnlinePlayers().stream().filter(player -> !message.getDisableWorlds().contains(player.getWorld().getName())).forEach(player -> {
            MiniAnnouncement.getAdventure().player(player).sendMessage(
                    MiniAnnouncement.setPlaceholders(player, message.getPrefix() + message.getMessages().get(ThreadLocalRandom.current().nextInt(message.getMessages().size())))
            );
        });
    }
}
