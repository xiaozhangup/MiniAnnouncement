package me.xiaozhangup.minian;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MiniAnnouncement extends JavaPlugin {

    public static MiniMessage mm = MiniMessage.miniMessage();
    public static Plugin plugin;
    public static List<Component> messages = new ArrayList<>();
    public static Long time = 1200L;
    public static Component prefix = Component.text("");
    public static Component reload = mm.deserialize("<white>插件已成功重载</white>");
    private static BukkitAudiences adventure;

    public static BukkitAudiences adventure() {
        if (adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return adventure;
    }

    @Override
    public void onEnable() {
        plugin = this;
        config();
        adventure = BukkitAudiences.create(this);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runner(), 0L, time);
        Bukkit.getPluginCommand("mareload").setExecutor((sender, command, label, args) -> {
            if (sender.isOp()) {
                adventure().sender(sender).sendMessage(prefix.append(reload));
                Bukkit.getPluginManager().disablePlugin(this);
                Bukkit.getPluginManager().enablePlugin(this);
            }
            return true;
        });
    }

    @Override
    public void onDisable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    public void config() {
        saveDefaultConfig();
        reloadConfig();
        messages.clear();

        time = getConfig().getLong("delay");
        prefix = mm.deserialize(getConfig().getString("prefix"));
        getConfig().getStringList("messages").forEach(s -> {
            messages.add(prefix.append(mm.deserialize(s)));
        });
    }
}
