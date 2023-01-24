package me.xiaozhangup.minian.util;

import me.xiaozhangup.minian.MiniAnnouncement;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;

/**
 * CustomCooking
 * me.mical.customcooking.util.LocaleUtil
 *
 * @author xiaomu
 * @since 2022/12/26 8:42 AM
 */
public class LocaleUtil {

    public static final String PREFIX = "[ <gradient:#8CAAEE:#85C1DC>MiniAnnouncement</gradient> ] ";

    /**
     * 快速发送带有前缀的文本信息.
     * @param source 对象
     * @param msg 内容
     * @param args 变量
     */
    public static void sendTo(@Nullable final CommandSender source, @Nullable final String msg, final Object... args) {
        sendWithoutPrefix(source, PREFIX + msg, args);
    }

    public static void sendToConsole(@Nullable final String msg, final Object... args) {
        sendTo(Bukkit.getConsoleSender(), msg, args);
    }

    public static void sendWithoutPrefix(@Nullable final CommandSender source, @Nullable final String msg, final Object... args) {
        if (source == null) {
            return;
        }
        final Audience user = MiniAnnouncement.getAdventure().sender(source);
        user.sendMessage(MiniMessage.miniMessage().deserialize(MessageFormat.format(msg == null ? "" : msg, args)));
    }
}
