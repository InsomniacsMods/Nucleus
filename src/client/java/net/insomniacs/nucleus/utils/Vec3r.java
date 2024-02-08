package net.insomniacs.nucleus.utils;

import com.mojang.serialization.Codec;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class Vec3r extends Vec3d {

    public Vec3r(double x, double y, double z) {
        super(Math.toRadians(x), Math.toRadians(y), Math.toRadians(z));
    }

    public Vec3r(List<Double> list) {
        this(list.get(0), list.get(1), list.get(2));
    }

    public List<Double> spread() {
        return List.of(x, y, z);
    }


    public static final Codec<Vec3r> CODEC = Codec.DOUBLE.listOf().comapFlatMap(
            (coordinates) -> Util.decodeFixedLengthList(coordinates, 3).map(Vec3r::new),
            Vec3r::spread
    );

    public static final Vec3r ZERO = new Vec3r(0, 0, 0);

}
