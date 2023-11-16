package net.insomniacs.nucleus.impl.items;

import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;

public class BSideMusicDiscItem extends MusicDiscItem {

    public BSideMusicDiscItem(Settings settings, int comparatorOutput, SoundEvent sound, int lengthInSeconds) {
        super(comparatorOutput, sound, settings, lengthInSeconds);
    }

}