package net.insomniacs.nucleus.api.blockbench.modelData;

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
            Vec3i from, Vec3i to, Vec2i uvOffset, float inflation,
            String name, String uuid, Vec3i pivotPoint, Vec3i rotation, boolean visible, boolean mirror
    ) {
        super();
        this.origin = from;
        this.size = new Vec3i(
                Math.abs(from.x - to.x),
                Math.abs(from.y - to.y),
                Math.abs(from.z - to.z)
        );
        this.uvOffset = uvOffset;
        this.inflation = inflation;

        this.name = name;
        this.uuid = uuid;
        this.pivotPoint = pivotPoint;
        this.rotation = rotation;
        this.visible = visible;
        this.mirror = mirror;
    }

    public static final Codec<Cube> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3i.CODEC.fieldOf("from").forGetter(null),
            Vec3i.CODEC.fieldOf("to").forGetter(null),
            Vec2i.CODEC.optionalFieldOf("uv_offset", Vec2i.NONE).forGetter(null),
            Codec.FLOAT.optionalFieldOf("inflate", 0F).forGetter(null),

            Codec.STRING.optionalFieldOf("name", "cube").forGetter(null),
            Codec.STRING.fieldOf("uuid").forGetter(null),
            Vec3i.CODEC.optionalFieldOf("origin", Vec3i.NONE).forGetter(null),
            Vec3i.CODEC.optionalFieldOf("rotation", Vec3i.NONE).forGetter(null),
            Codec.BOOL.optionalFieldOf("visible", true).forGetter(null),
            Codec.BOOL.optionalFieldOf("mirror_uv", false).forGetter(null)
    ).apply(instance, Cube::new));

    public String toString() {
        return "Cube[" + this.uuid + "]";
    }

    public ModelPartBuilder modelData() {
        return ModelPartBuilder.create()
                .uv(this.uvOffset.x, this.uvOffset.y)
                .mirrored(this.mirror)
                .cuboid("cube",
                        0, 0, 0,
                        this.size.x, this.size.y, this.size.z, new Dilation(this.inflation)
                );
    }

    public ModelTransform modelTransformation() {
        return ModelTransform.of(
                this.origin.x, (this.origin.y - 12) * -1, this.origin.z,
//                this.rotation.x, this.rotation.y, this.rotation.z
                0, 0, 0
        );
    }

}