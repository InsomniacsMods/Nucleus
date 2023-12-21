package net.insomniacs.nucleus.api.geo_model.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Texture {

    public int width;
    public int height;

    public Texture(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static final Codec<Texture> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("texture_width").forGetter(null),
            Codec.INT.fieldOf("texture_height").forGetter(null)
    ).apply(instance, Texture::new));

}
