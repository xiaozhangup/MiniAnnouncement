package me.xiaozhangup.minian.task;

import me.xiaozhangup.minian.ConfigReader;
import me.xiaozhangup.minian.MiniAnnouncement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.task.TitleTask
 *
 * @author xiaomu
 * @since 2022/12/31 4:17 PM
 */
public class TitleTask {

    public TitleTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ConfigReader.Title.ENABLE) {
                    Bukkit.getOnlinePlayers().stream().filter(player -> !ConfigReader.Title.DISABLE_WORLDS.contains(player.getWorld().getName())).forEach(player -> {
                        final int index = ThreadLocalRandom.current().nextInt(ConfigReader.Title.MESSAGES.size());
                        final Map.Entry<Component, Component> message = new ArrayList<>(ConfigReader.Title.MESSAGES.entrySet()).get(index);
                        final Title.Times times = Title.Times.times(Duration.ofMillis(ConfigReader.Title.FADE_IN), Duration.ofMillis(ConfigReader.Title.STAY_ON_SCREEN), Duration.ofMillis(ConfigReader.Title.FADE_OUT));
                        final Title title = Title.title(message.getKey(), message.getValue(), times);
                        MiniAnnouncement.getAdventure().player(player).showTitle(title);
                    });
                }
            }
        }.runTaskTimerAsynchronously(MiniAnnouncement.getInstance(), 0L, ConfigReader.Title.DELAY * 20L);
    }
}
