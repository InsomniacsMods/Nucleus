package net.insomniacs.nucleus.api.sound;

import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;

public record BundleSoundGroup (
        float volume,
        float pitch,
        SoundEvent addSound,
        SoundEvent removeSound,
        SoundEvent dropSound
) {

    public void playAddSound(Entity entity) {
        playSound(entity, addSound);
    }

    public void playRemoveSound(Entity entity) {
        playSound(entity, removeSound);
    }

    public void playDropSound(Entity entity) {
        playSound(entity, dropSound);
    }

    private void playSound(Entity entity, SoundEvent sound) {
        entity.playSound(sound, volume, pitch + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

}