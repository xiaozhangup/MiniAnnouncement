package me.xiaozhangup.minian.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.xiaozhangup.minian.object.struct.Title;

import java.util.List;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.object.TitleMessage
 *
 * @author xiaomu
 * @since 2023/1/24 3:18 PM
 */
@Data
@AllArgsConstructor
public class TitleMessage {

    private List<Title> messages;
    private List<String> disableWorlds;
    private long delay;
    private String prefix;
    private List<String> time;
}
