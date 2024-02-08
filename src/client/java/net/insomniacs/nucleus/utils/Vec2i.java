package net.insomniacs.nucleus.utils;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3i;

import java.util.stream.IntStream;

public class Vec2i {
    public int x; public int y;

    public static Vec2i ZERO = new Vec2i(0, 0);


    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2i(int... xyz) {
        this.x = xyz[0];
        this.y = xyz[1];
    }


    public Vec2i add(int x, int y) {
        return new Vec2i(this.x + x, this.y + y);
    }

    public Vec2i add(Vec2i vec) {
        return this.add(vec.x, vec.y);
    }

    public Vec2i subtract(Vec2i vec) {
        return this.add(-vec.x, -vec.y);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("x", x).add("y", y).toString();
    }

    public String toShortString() {
        return x + ", " + y;
    }

    public static final Codec<Vec2i> CODEC = Codec.INT_STREAM.comapFlatMap(
            stream -> Util.decodeFixedLengthArray(stream, 2).map(Vec2i::new),
            vec -> IntStream.of(vec.x, vec.y)
    ).stable();

}