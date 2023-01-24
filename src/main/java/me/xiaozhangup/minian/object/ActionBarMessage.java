package me.xiaozhangup.minian.object;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.object.ActionBarMessage
 *
 * @author xiaomu
 * @since 2023/1/24 3:12 PM
 */
@Data
@AllArgsConstructor
public class ActionBarMessage {

    private List<String> messages;
    private List<String> disableWorlds;
    private long delay;
    private String prefix;
    private List<String> time;
}
