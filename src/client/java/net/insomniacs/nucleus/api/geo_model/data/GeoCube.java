package net.insomniacs.nucleus.api.geo_model.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.utils.Vec2i;
import net.insomniacs.nucleus.utils.Vec3r;
import net.minecraft.client.model.*;
import net.minecraft.util.math.Vec3d;

public record GeoCube (
        Vec3d origin, Vec3d size, Vec3d pivot, Vec3r rotation,
        Vec2i uvOffset, float scale, boolean visible, boolean mirror
) {

    public static final Codec<GeoCube> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3d.CODEC.fieldOf("origin").forGetter(GeoCube::origin),
            Vec3d.CODEC.fieldOf("size").forGetter(GeoCube::size),
            Vec3d.CODEC.optionalFieldOf("pivot", Vec3d.ZERO).forGetter(GeoCube::pivot),
            Vec3r.CODEC.optionalFieldOf("rotation", Vec3r.ZERO).forGetter(GeoCube::rotation),

            Vec2i.CODEC.optionalFieldOf("uv", Vec2i.ZERO).forGetter(GeoCube::uvOffset),
            Codec.FLOAT.optionalFieldOf("inflate", 0F).forGetter(GeoCube::scale),
            Codec.BOOL.optionalFieldOf("visible", true).forGetter(GeoCube::visible),
            Codec.BOOL.optionalFieldOf("mirror", false).forGetter(GeoCube::mirror)
    ).apply(instance, GeoCube::new));

    public void append(ModelPartData modelData, String name) {
        modelData.addChild(
                name,
                getBuilder(pivot),
                getTransformation(pivot)
        );
    }

    public ModelPartBuilder getBuilder(Vec3d parentPivot) {
        Vec3d offset = new Vec3d(
                (float)parentPivot.x - (origin.x + size.x),
                (float)-origin.y - size.y + parentPivot.y,
//                (float)origin.y - parentPivot.y,
                (float)origin.z - parentPivot.z
        );

        return ModelPartBuilder.create()
                .uv(uvOffset.x, uvOffset.y)
                .mirrored(mirror)
                .cuboid("cube",
                        (float)offset.x, (float)offset.y, (float)offset.z,
                        (float)size.x, (float)size.y, (float)size.z, new Dilation(scale)
                );
    }

    public ModelTransform getTransformation(Vec3d pivot) {
        return ModelTransform.of(
                (float)pivot.x, (float)pivot.y, (float)pivot.z,
                (float)rotation.x, (float)rotation.y, (float)rotation.z
        );
    }


    public String toString() {
        return "Cube[]";
    }

}