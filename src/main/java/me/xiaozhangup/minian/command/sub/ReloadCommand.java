package me.xiaozhangup.minian.command.sub;

import me.xiaozhangup.minian.MiniAnnouncement;
import me.xiaozhangup.minian.command.SubCommand;
import me.xiaozhangup.minian.command.type.AdminCommand;
import me.xiaozhangup.minian.util.LocaleUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.command.sub.ReloadCommand
 *
 * @author xiaomu
 * @since 2023/1/24 10:44 PM
 */
public class ReloadCommand extends SubCommand implements AdminCommand {

    public ReloadCommand() {
        super("reload", "/miniannouncement reload", "重载插件");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final MiniAnnouncement plugin = MiniAnnouncement.getInstance();
        plugin.getServer().getPluginManager().disablePlugin(plugin);
        plugin.getServer().getPluginManager().enablePlugin(plugin);
        LocaleUtil.sendTo(sender, "已成功重载插件.");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
