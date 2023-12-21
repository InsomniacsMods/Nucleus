package net.insomniacs.nucleus.api.geo.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.api.utils.Vec2i;
import net.insomniacs.nucleus.api.utils.Vec3i;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelTransform;

public class Cube extends Element {
    public Vec3i origin;
    public Vec3i size;
    public Vec2i uvOffset;
    public float inflation;

    public Cube(
            Vec3i origin, Vec3i size, Vec3i pivot, Vec3i rotation,
            Vec2i uvOffset, float inflation, boolean visible, boolean mirror
    ) {
        super();
        this.origin = new Vec3i(
                origin.x - pivot.x,
                origin.y - pivot.y,
                origin.z - pivot.z
        );
        this.size = size;
        this.pivot = new Vec3i(
                origin.x + pivot.x,
                origin.y + pivot.y,
                origin.z + pivot.z
        );
        this.rotation = rotation;

        this.uvOffset = uvOffset;
        this.inflation = inflation;
        this.visible = visible;
        this.mirror = mirror;
    }

    public static final Codec<Cube> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3i.CODEC.fieldOf("origin").forGetter(null),
            Vec3i.CODEC.fieldOf("size").forGetter(null),
            Vec3i.CODEC.optionalFieldOf("pivot", Vec3i.NONE).forGetter(null),
            Vec3i.CODEC.optionalFieldOf("rotation", Vec3i.NONE).forGetter(null),
            Vec2i.CODEC.optionalFieldOf("uv", Vec2i.NONE).forGetter(null),
            Codec.FLOAT.optionalFieldOf("inflate", 0F).forGetter(null),
            Codec.BOOL.optionalFieldOf("visible", true).forGetter(null),
            Codec.BOOL.optionalFieldOf("mirror", false).forGetter(null)
    ).apply(instance, Cube::new));

    public String toString() {
        return "Cube[]";
    }

    public ModelPartBuilder builder() {
        return ModelPartBuilder.create()
                .uv(this.uvOffset.x, this.uvOffset.y)
                .mirrored(this.mirror)
                .cuboid("cube",
                        this.origin.x, this.origin.y, this.origin.z,
                        this.size.x, this.size.y, this.size.z, new Dilation(this.inflation)
                );
    }

    public ModelTransform transformer() {
        return ModelTransform.of(
                this.pivot.x, this.pivot.y, this.pivot.z,
                this.rotation.x, this.rotation.y, this.rotation.z
        );
    }

}