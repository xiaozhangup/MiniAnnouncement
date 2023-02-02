package me.xiaozhangup.minian.command;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * CustomCooking
 * me.mical.customcooking.core.command.CommandExecutor
 *
 * @author xiaomu
 * @since 2022/12/26 2:03 PM
 */
public abstract class SubCommand {

    @Getter
    private final String name, usage, description;

    public SubCommand(String name, String usage, String description) {
        this.name = name;
        this.usage = usage;
        this.description = description;
    }

    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    @Nullable
    public abstract List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args);
}
