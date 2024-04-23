package net.insomniacs.nucleus.api.utils;

import net.minecraft.sound.SoundEvent;

public record BundleItemSoundGroup (float volume, float pitch, SoundEvent addSound, SoundEvent removeSound, SoundEvent dropSound) {

    @Override
    public float volume() {
        return volume;
    }

    @Override
    public float pitch() {
        return pitch;
    }

    @Override
    public SoundEvent addSound() {
        return addSound;
    }

    @Override
    public SoundEvent removeSound() {
        return removeSound;
    }

    @Override
    public SoundEvent dropSound() {
        return dropSound;
    }

}