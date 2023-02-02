package me.xiaozhangup.minian.command.sub;

import me.xiaozhangup.minian.command.SubCommand;
import me.xiaozhangup.minian.command.type.AdminCommand;
import me.xiaozhangup.minian.config.ActionBarConfig;
import me.xiaozhangup.minian.config.BossBarConfig;
import me.xiaozhangup.minian.config.MessageConfig;
import me.xiaozhangup.minian.config.TitleConfig;
import me.xiaozhangup.minian.util.LocaleUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.command.sub.StartCommand
 *
 * @author xiaomu
 * @since 2023/1/24 5:06 PM
 */
public class StartCommand extends SubCommand implements AdminCommand {

    public StartCommand() {
        super("start", "/miniannouncement start <type> <id>", "启用某一个定时公告");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            LocaleUtil.sendTo(sender, "请指定要操作的公告的类型.");
            return true;
        }
        if (args.length == 2) {
            LocaleUtil.sendTo(sender, "请指定要操作公告的标识.");
            return true;
        }
        if (args.length == 3) {
            final String id = args[2];
            switch (args[1].toLowerCase()) {
                case "actionbar":
                    if (!ActionBarConfig.DATA.containsKey(id)) {
                        LocaleUtil.sendTo(sender, "不存在该公告.");
                        return true;
                    }
                    if (!ActionBarConfig.STOPPED.contains(id)) {
                        LocaleUtil.sendTo(sender, "该公告正在运行.");
                        return true;
                    }
                    ActionBarConfig.STOPPED.remove(id);
                    LocaleUtil.sendTo(sender, "已启用该公告.");
                    break;
                case "bossbar":
                    if (!BossBarConfig.DATA.containsKey(id)) {
                        LocaleUtil.sendTo(sender, "不存在该公告.");
                        return true;
                    }
                    if (!BossBarConfig.STOPPED.contains(id)) {
                        LocaleUtil.sendTo(sender, "该公告正在运行.");
                        return true;
                    }
                    BossBarConfig.STOPPED.remove(id);
                    LocaleUtil.sendTo(sender, "已启用该公告.");
                    break;
                case "message":
                    if (!MessageConfig.DATA.containsKey(id)) {
                        LocaleUtil.sendTo(sender, "不存在该公告.");
                        return true;
                    }
                    if (!MessageConfig.STOPPED.contains(id)) {
                        LocaleUtil.sendTo(sender, "该公告正在运行.");
                        return true;
                    }
                    MessageConfig.STOPPED.remove(id);
                    LocaleUtil.sendTo(sender, "已启用该公告.");
                    break;
                case "title":
                    if (!TitleConfig.DATA.containsKey(id)) {
                        LocaleUtil.sendTo(sender, "不存在该公告.");
                        return true;
                    }
                    if (!TitleConfig.STOPPED.contains(id)) {
                        LocaleUtil.sendTo(sender, "该公告正在运行.");
                        return true;
                    }
                    TitleConfig.STOPPED.remove(id);
                    LocaleUtil.sendTo(sender, "已启用该公告.");
                    break;
                default:
                    LocaleUtil.sendTo(sender, "不存在该公告类型.");
                    break;
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length <= 2) {
            return Arrays.asList("actionbar", "bossbar", "message", "title");
        }
        if (args.length == 3) {
            switch (args[2]) {
                case "actionbar":
                    return ActionBarConfig.STOPPED;
                case "bossbar":
                    return BossBarConfig.STOPPED;
                case "message":
                    return MessageConfig.STOPPED;
                case "title":
                    return TitleConfig.STOPPED;
                default:
                    return null;
            }
        }
        return null;
    }
}
