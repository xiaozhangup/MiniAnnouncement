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
import java.util.stream.Collectors;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.command.sub.StopCommand
 *
 * @author xiaomu
 * @since 2023/1/24 5:06 PM
 */
public class StopCommand extends SubCommand implements AdminCommand {

    public StopCommand() {
        super("stop", "/miniannouncement stop <type> <id>", "停止某一个定时公告");
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
                    if (ActionBarConfig.STOPPED.contains(id)) {
                        LocaleUtil.sendTo(sender, "该公告已经被停止.");
                        return true;
                    }
                    ActionBarConfig.STOPPED.add(id);
                    LocaleUtil.sendTo(sender, "已停止该公告.");
                    break;
                case "bossbar":
                    if (!BossBarConfig.DATA.containsKey(id)) {
                        LocaleUtil.sendTo(sender, "不存在该公告.");
                        return true;
                    }
                    if (BossBarConfig.STOPPED.contains(id)) {
                        LocaleUtil.sendTo(sender, "该公告已经被停止.");
                        return true;
                    }
                    BossBarConfig.STOPPED.add(id);
                    LocaleUtil.sendTo(sender, "已停止该公告.");
                    break;
                case "message":
                    if (!MessageConfig.DATA.containsKey(id)) {
                        LocaleUtil.sendTo(sender, "不存在该公告.");
                        return true;
                    }
                    if (MessageConfig.STOPPED.contains(id)) {
                        LocaleUtil.sendTo(sender, "该公告已经被停止.");
                        return true;
                    }
                    MessageConfig.STOPPED.add(id);
                    LocaleUtil.sendTo(sender, "已停止该公告.");
                    break;
                case "title":
                    if (!TitleConfig.DATA.containsKey(id)) {
                        LocaleUtil.sendTo(sender, "不存在该公告.");
                        return true;
                    }
                    if (TitleConfig.STOPPED.contains(id)) {
                        LocaleUtil.sendTo(sender, "该公告已经被停止.");
                        return true;
                    }
                    TitleConfig.STOPPED.add(id);
                    LocaleUtil.sendTo(sender, "已停止该公告.");
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
                    return ActionBarConfig.DATA.keySet().stream().filter(id -> !ActionBarConfig.STOPPED.contains(id)).collect(Collectors.toList());
                case "bossbar":
                    return BossBarConfig.DATA.keySet().stream().filter(id -> !BossBarConfig.STOPPED.contains(id)).collect(Collectors.toList());
                case "message":
                    return MessageConfig.DATA.keySet().stream().filter(id -> !MessageConfig.STOPPED.contains(id)).collect(Collectors.toList());
                case "title":
                    return TitleConfig.DATA.keySet().stream().filter(id -> !TitleConfig.STOPPED.contains(id)).collect(Collectors.toList());
                default:
                    return null;
            }
        }
        return null;
    }
}
