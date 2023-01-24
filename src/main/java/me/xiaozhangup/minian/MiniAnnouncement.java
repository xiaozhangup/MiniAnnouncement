package me.xiaozhangup.minian;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.xiaozhangup.minian.command.CommandHandler;
import me.xiaozhangup.minian.config.ActionBarConfig;
import me.xiaozhangup.minian.config.BossBarConfig;
import me.xiaozhangup.minian.config.MessageConfig;
import me.xiaozhangup.minian.config.TitleConfig;
import me.xiaozhangup.minian.service.Metrics;
import me.xiaozhangup.minian.task.ActionBarTask;
import me.xiaozhangup.minian.task.BossBarTask;
import me.xiaozhangup.minian.task.MessageTask;
import me.xiaozhangup.minian.task.TitleTask;
import me.xiaozhangup.minian.util.LocaleUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class MiniAnnouncement extends JavaPlugin {

    @Getter
    private static MiniAnnouncement instance;
    @Getter
    private static BukkitAudiences adventure;
    @Getter
    private static CommandHandler commandHandler;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        instance = this;
        adventure = BukkitAudiences.create(this);
        ConfigReader.loadConfig();
        ActionBarConfig.loadActionBars();
        BossBarConfig.loadBossBars();
        MessageConfig.loadMessages();
        TitleConfig.loadTitles();
        new ActionBarTask();
        new BossBarTask();
        new MessageTask();
        new TitleTask();
        commandHandler = new CommandHandler();
        if (ConfigReader.B_STATS) {
            new Metrics(this, 17243);
            LocaleUtil.sendToConsole("已启用 bStats 数据统计.");
            LocaleUtil.sendToConsole("若您需要禁用此功能, 一般情况下可于配置文件 config.yml 中编辑或新增 \"bStats: false\" 关闭此功能.");
        } else {
            LocaleUtil.sendToConsole("bStats 数据统计已被禁用.");
        }
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    @NotNull
    public static Component setPlaceholders(@NotNull Player player, @NotNull String origin) {
        String result = origin;
        if (instance.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            result = PlaceholderAPI.setPlaceholders(player, result);
        }
        return miniMessage.deserialize(result);
    }
}
