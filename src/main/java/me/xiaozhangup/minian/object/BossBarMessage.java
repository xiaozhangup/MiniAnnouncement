package me.xiaozhangup.minian.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.xiaozhangup.minian.object.struct.BossBar;

import java.util.List;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.object.BossBarMessage
 *
 * @author xiaomu
 * @since 2023/1/24 3:15 PM
 */
@Data
@AllArgsConstructor
public class BossBarMessage {

    private List<BossBar> messages;
    private List<String> disableWorlds;
    private long delay;
    private String prefix;
    private List<String> time;
}
