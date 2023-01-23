package me.xiaozhangup.minian.task;

import me.xiaozhangup.minian.ConfigReader;
import me.xiaozhangup.minian.MiniAnnouncement;
import me.xiaozhangup.minian.util.TimeUtil;
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
        // 普通的 Interval Task
        new BukkitRunnable() {
            @Override
            public void run() {
                send();
            }
        }.runTaskTimerAsynchronously(MiniAnnouncement.getInstance(), 0L, ConfigReader.Title.DELAY * 20L);

        // 定时任务
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ConfigReader.Title.TIME.contains(TimeUtil.getNow())) {
                    send();
                }
            }
        }.runTaskTimerAsynchronously(MiniAnnouncement.getInstance(), 0L, 20L);
    }

    private void send() {
        if (ConfigReader.Title.ENABLE) {
            Bukkit.getOnlinePlayers().stream().filter(player -> !ConfigReader.Title.DISABLE_WORLDS.contains(player.getWorld().getName())).forEach(player -> {
                final int index = ThreadLocalRandom.current().nextInt(ConfigReader.Title.MESSAGES.size());
                final Map.Entry<String, String> message = new ArrayList<>(ConfigReader.Title.MESSAGES.entrySet()).get(index);
                final Component mainTitle = MiniAnnouncement.setPlaceholders(player, ConfigReader.Title.PREFIX + message.getKey());
                final Component subtitle = MiniAnnouncement.setPlaceholders(player, ConfigReader.Title.PREFIX + message.getValue());
                final Title.Times times = Title.Times.times(Duration.ofMillis(ConfigReader.Title.FADE_IN), Duration.ofMillis(ConfigReader.Title.STAY_ON_SCREEN), Duration.ofMillis(ConfigReader.Title.FADE_OUT));
                final Title title = Title.title(mainTitle, subtitle, times);
                MiniAnnouncement.getAdventure().player(player).showTitle(title);
            });
        }
    }
}
