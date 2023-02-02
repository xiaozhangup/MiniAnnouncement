package me.xiaozhangup.minian.task;

import me.xiaozhangup.minian.MiniAnnouncement;
import me.xiaozhangup.minian.config.TitleConfig;
import me.xiaozhangup.minian.object.TitleMessage;
import me.xiaozhangup.minian.util.TimeUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
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
        for (Map.Entry<String, TitleMessage> entry : TitleConfig.DATA.entrySet()) {
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

    private void send(final String id, final TitleMessage message) {
        if (TitleConfig.STOPPED.contains(id)) return;
        Bukkit.getOnlinePlayers().stream().filter(player -> !message.getDisableWorlds().contains(player.getWorld().getName())).forEach(player -> {
            final me.xiaozhangup.minian.object.struct.Title msg = message.getMessages().get(ThreadLocalRandom.current().nextInt(message.getMessages().size()));
            final Component mainTitle = MiniAnnouncement.setPlaceholders(player, message.getPrefix() + msg.getTitle());
            final Component subtitle = MiniAnnouncement.setPlaceholders(player, message.getPrefix() + msg.getSubtitle());
            final Title.Times times = Title.Times.times(Duration.ofMillis(msg.getFadeIn()), Duration.ofMillis(msg.getStayOnScreen()), Duration.ofMillis(msg.getFadeOut()));
            final Title title = Title.title(mainTitle, subtitle, times);
            MiniAnnouncement.getAdventure().player(player).showTitle(title);
        });
    }
}
