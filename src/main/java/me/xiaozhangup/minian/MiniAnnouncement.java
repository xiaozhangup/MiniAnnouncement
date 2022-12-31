package me.xiaozhangup.minian;

import me.xiaozhangup.minian.task.ActionBarTask;
import me.xiaozhangup.minian.task.BossBarTask;
import me.xiaozhangup.minian.task.MessageTask;
import me.xiaozhangup.minian.task.TitleTask;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiniAnnouncement extends JavaPlugin {

    private static MiniAnnouncement instance;
    private static BukkitAudiences adventure;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        instance = this;
        adventure = BukkitAudiences.create(this);
        ConfigReader.loadConfig();
        new ActionBarTask();
        new BossBarTask();
        new MessageTask();
        new TitleTask();
    }

    @Override
    public void onDisable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    public static BukkitAudiences getAdventure() {
        if (adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return adventure;
    }

    public static MiniMessage getMiniMessage() {
        return miniMessage;
    }

    public static MiniAnnouncement getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mareload")) {
            if (sender.isOp()) {
                getServer().getPluginManager().disablePlugin(this);
                getServer().getPluginManager().enablePlugin(this);
                sender.sendMessage("插件已成功重载");
            }
        }
        return true;
    }
}
