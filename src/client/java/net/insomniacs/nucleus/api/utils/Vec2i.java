package net.insomniacs.nucleus.api.utils;

import com.mojang.serialization.Codec;

import java.util.stream.IntStream;

public class Vec2i {
    public int x; public int y;

    public static Vec2i NONE = new Vec2i(0, 0);

    public Vec2i(int[] xyz) {
        this.x = xyz[0];
        this.y = xyz[1];
    }

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public static final Codec<Vec2i> CODEC = Codec.INT_STREAM.comapFlatMap(
            stream -> net.minecraft.util.Util.decodeFixedLengthArray(stream, 2).map(Vec2i::new),
            vec -> IntStream.of(vec.x, vec.y)
    ).stable();
}