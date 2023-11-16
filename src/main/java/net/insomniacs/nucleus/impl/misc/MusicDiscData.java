package net.insomniacs.nucleus.impl.misc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.sound.SoundEvent;

public record MusicDiscData(int comparatorOutput, SoundEvent sound, int length) {

    @Override
    public int comparatorOutput() {
        return comparatorOutput;
    }

    @Override
    public SoundEvent sound() {
        return sound;
    }

    @Override
    public int length() {
        return length;
    }

    public static final Codec<MusicDiscData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("comparator_output").forGetter(MusicDiscData::comparatorOutput),
            SoundEvent.CODEC.fieldOf("sound").forGetter(MusicDiscData::sound),
            Codec.INT.fieldOf("length").forGetter(MusicDiscData::length)
    ).apply(instance, MusicDiscData::new));

}
