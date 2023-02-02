package me.xiaozhangup.minian.object.struct;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * MiniAnnouncement
 * me.xiaozhangup.minian.object.struct.BossBar
 *
 * @author xiaomu
 * @since 2023/1/24 3:22 PM
 */
@Data
@AllArgsConstructor
public class BossBar {

    private String color;
    private long stay;
    private String message;
}
