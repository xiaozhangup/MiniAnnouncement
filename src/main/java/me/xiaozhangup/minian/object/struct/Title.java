package me.xiaozhangup.minian.object.struct;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.object.struct.Title
 *
 * @author xiaomu
 * @since 2023/1/24 3:20 PM
 */
@Data
@AllArgsConstructor
public class Title {

    private String title;
    private String subtitle;
    private long fadeIn;
    private long stayOnScreen;
    private long fadeOut;
}
