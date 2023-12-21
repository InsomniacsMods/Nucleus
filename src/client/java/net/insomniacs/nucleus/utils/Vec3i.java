package net.insomniacs.nucleus.utils;

import com.mojang.serialization.Codec;

import java.util.stream.IntStream;

public class Vec3i {
    public int x; public int y; public int z;

    public static Vec3i NONE = new Vec3i(0, 0, 0);

    public Vec3i(int[] xyz) {
        this.x = xyz[0];
        this.y = xyz[1];
        this.z = xyz[2];
    }

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public static final Codec<Vec3i> CODEC = Codec.INT_STREAM.comapFlatMap(
            stream -> net.minecraft.util.Util.decodeFixedLengthArray(stream, 3).map(Vec3i::new),
            vec -> IntStream.of(vec.x, vec.y, vec.z)
    ).stable();
}