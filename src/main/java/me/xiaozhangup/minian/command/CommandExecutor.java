package me.xiaozhangup.minian.command;

import me.xiaozhangup.minian.MiniAnnouncement;
import me.xiaozhangup.minian.command.type.AdminCommand;
import me.xiaozhangup.minian.util.LocaleUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CustomCooking
 * me.mical.customcooking.core.command.CommandExecutor
 *
 * @author xiaomu
 * @since 2022/12/26 2:03 PM
 */
public class CommandExecutor implements org.bukkit.command.CommandExecutor, TabCompleter {

    private void sendHelp(CommandSender sender) {
        MiniAnnouncement.getCommandHandler().getCommands().stream()
                .filter(subCommand -> !(subCommand instanceof AdminCommand))
                .forEach(subCommand -> LocaleUtil.sendTo(sender, "<gray>{0} <yellow>{1}", subCommand.getUsage(), subCommand.getDescription()));
        if (sender.isOp()) {
            MiniAnnouncement.getCommandHandler().getCommands().stream()
                    .filter(subCommand -> subCommand instanceof AdminCommand)
                    .forEach(subCommand -> LocaleUtil.sendTo(sender, "<gray>{0} <yellow>{1}", subCommand.getUsage(), subCommand.getDescription()));
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length <= 1) {
            return MiniAnnouncement.getCommandHandler().getCommands().stream().map(SubCommand::getName).collect(Collectors.toList());
        }
        String arg = args[0];
        SubCommand subCommand = MiniAnnouncement.getCommandHandler().getSubCommand(arg);
        if (subCommand == null) {
            subCommand = MiniAnnouncement.getCommandHandler().getSubCommand("*");
        }
        if (subCommand == null) {
            return null;
        }
        return subCommand.onTabComplete(sender, command, alias, args);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return false;
        } else {
            String arg = args[0];
            SubCommand subCommand = MiniAnnouncement.getCommandHandler().getSubCommand(arg);
            if (subCommand == null) {
                subCommand = MiniAnnouncement.getCommandHandler().getSubCommand("*");
            }
            if (subCommand == null) {
                return false;
            }
            if (subCommand instanceof AdminCommand) {
                if (!sender.isOp()) {
                    return true;
                }
            }
            return subCommand.onCommand(sender, command, label, args);
        }
    }
}
