package me.xiaozhangup.minian.command;

import lombok.Getter;
import me.xiaozhangup.minian.command.sub.MigrateCommand;
import me.xiaozhangup.minian.command.sub.StartCommand;
import me.xiaozhangup.minian.command.sub.StopCommand;
import me.xiaozhangup.minian.util.LocaleUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

import java.util.HashSet;
import java.util.Set;

/**
 * CustomCooking
 * me.mical.customcooking.core.command.CommandHandler
 *
 * @author xiaomu
 * @since 2022/12/26 2:03 PM
 */
public class CommandHandler {

    @Getter
    private final Set<SubCommand> commands = new HashSet<>();

    public CommandHandler() {
        final PluginCommand command = Bukkit.getPluginCommand("customcooking");
        if (command != null) {
            final CommandExecutor executor = new CommandExecutor();
            command.setExecutor(executor);
            command.setTabCompleter(executor);
        }
        register(new StartCommand());
        register(new StopCommand());
        register(new MigrateCommand());
    }

    public void register(final SubCommand cmd) {
        if (commands.stream().anyMatch(sub -> sub.getName().equalsIgnoreCase(cmd.getName()))) {
            LocaleUtil.sendToConsole("注册 子命令 时遇到错误(重复注册子命令 {0}).");
            return;
        }
        commands.add(cmd);
    }

    public SubCommand getSubCommand(String cmd) {
        for (SubCommand command : commands) {
            if (command.getName().equalsIgnoreCase(cmd)) {
                return command;
            }
        }
        return null;
    }
}
