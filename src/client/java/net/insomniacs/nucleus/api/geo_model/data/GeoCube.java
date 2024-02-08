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
            Vec3d.CODEC.fieldOf("origin").forGetter(null),
            Vec3d.CODEC.fieldOf("size").forGetter(null),
            Vec3d.CODEC.optionalFieldOf("pivot", Vec3d.ZERO).forGetter(null),
            Vec3r.CODEC.optionalFieldOf("rotation", Vec3r.ZERO).forGetter(null),

            Vec2i.CODEC.optionalFieldOf("uv", Vec2i.ZERO).forGetter(null),
            Codec.FLOAT.optionalFieldOf("inflate", 0F).forGetter(null),
            Codec.BOOL.optionalFieldOf("visible", true).forGetter(null),
            Codec.BOOL.optionalFieldOf("mirror", false).forGetter(null)
    ).apply(instance, GeoCube::new));


    public ModelPartBuilder partBuilder(Vec3d parentPivot) {

        Vec3d pivot = this.pivot.subtract(parentPivot);
        Vec3d to = this.origin.add(this.size);
        Vec3d offset = new Vec3d(
                (float)-(pivot.x - to.x),
                (float)(pivot.y + to.y),
//                (float)(origin.z + parentPivot.z)
                (float)-(origin.z + pivot.z)
        );

        System.out.println("offset:           " + offset);

        return ModelPartBuilder.create()
                .uv(uvOffset.x, uvOffset.y)
                .mirrored(mirror)
                .cuboid("cube",
                        (float)offset.x, (float)offset.y, (float)offset.z,
                        (float)size.x, (float)size.y, (float)size.z, new Dilation(scale)
                );
    }

    public ModelTransform partTransform(Vec3d parentPivot) {
        Vec3d pivot = new Vec3d(
                -(parentPivot.x - this.pivot.x),
                // TODO fix this one
                (this.pivot.y - parentPivot.y + size.y),
                -(parentPivot.z - this.pivot.z)
        );
        return ModelTransform.of(
                (float)pivot.x, (float)pivot.y, (float)pivot.z,
                (float)rotation.x, (float)rotation.y, (float)rotation.z
        );
    }


    public String toString() {
        return "Cube[]";
    }

}