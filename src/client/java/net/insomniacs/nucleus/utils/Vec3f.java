package net.insomniacs.nucleus.utils;

import com.mojang.serialization.Codec;
import net.minecraft.util.Util;

import java.util.List;

public class Vec3f {

    public float x;
    public float y;
    public float z;

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3f(List<Float> list) {
        this(list.get(0), list.get(1), list.get(2));
    }

    public List<Float> spread() {
        return List.of(x, y, z);
    }


    public static final Codec<Vec3f> CODEC = Codec.FLOAT.listOf().comapFlatMap(
            (coordinates) -> Util.decodeFixedLengthList(coordinates, 3).map(Vec3f::new),
            Vec3f::spread
    );

    public static final Vec3f ZERO = new Vec3f(0, 0, 0);

}
