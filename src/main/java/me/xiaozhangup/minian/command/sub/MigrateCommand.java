package me.xiaozhangup.minian.command.sub;

import me.xiaozhangup.minian.command.SubCommand;
import me.xiaozhangup.minian.command.type.AdminCommand;
import me.xiaozhangup.minian.migrate.ConfigMigrator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.command.sub.MigrateCommand
 *
 * @author xiaomu
 * @since 2023/1/24 4:59 PM
 */
public class MigrateCommand extends SubCommand implements AdminCommand {

    public MigrateCommand() {
        super("migrate", "/miniannouncement migrate", "迁移旧配置文件");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigMigrator.migrate(sender);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
