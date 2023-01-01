package me.xiaozhangup.minian.task;

import me.xiaozhangup.minian.ConfigReader;
import me.xiaozhangup.minian.MiniAnnouncement;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

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
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ConfigReader.Message.ENABLE) {
                    Bukkit.getOnlinePlayers().stream().filter(player -> !ConfigReader.Message.DISABLE_WORLDS.contains(player.getWorld().getName())).forEach(player -> {
                        MiniAnnouncement.getAdventure().player(player).sendMessage(
                                MiniAnnouncement.setPlaceholders(player, ConfigReader.Message.PREFIX + ConfigReader.Message.MESSAGES.get(ThreadLocalRandom.current().nextInt(ConfigReader.Message.MESSAGES.size())))
                        );
                    });
                }
            }
        }.runTaskTimerAsynchronously(MiniAnnouncement.getInstance(), 0L, ConfigReader.Message.DELAY * 20L);
    }
}
