package me.xiaozhangup.minian;

import org.bukkit.Bukkit;

import java.util.Random;

public class Runner implements Runnable {
    Random random = new Random();

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            MiniAnnouncement.adventure().player(player).sendMessage(
                    MiniAnnouncement.messages.get(random.nextInt(
                            MiniAnnouncement.messages.size()
                    ))
            );
        });
    }
}
