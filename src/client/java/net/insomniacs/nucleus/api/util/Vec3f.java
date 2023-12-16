package net.insomniacs.nucleus.api.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Vec3f {
    public float x; public float y; public float z;

    public static Vec3f NONE = new Vec3f(0, 0, 0);

    public Vec3f(int[] xyz) {
        this.x = xyz[0];
        this.y = xyz[1];
        this.z = xyz[2];
    }

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

}