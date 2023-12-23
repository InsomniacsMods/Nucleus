package net.insomniacs.nucleus.api.geo_model.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.utils.Vec2i;
import net.insomniacs.nucleus.utils.Vec3f;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;

public record GeoCube (
        Vec3f origin, Vec3f size,
        Vec2i uvOffset, float inflation, boolean visible, boolean mirror
) {

    public static final Codec<GeoCube> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3f.CODEC.fieldOf("origin").forGetter(null),
            Vec3f.CODEC.fieldOf("size").forGetter(null),

            Vec2i.CODEC.optionalFieldOf("uv", Vec2i.NONE).forGetter(null),
            Codec.FLOAT.optionalFieldOf("inflate", 0F).forGetter(null),
            Codec.BOOL.optionalFieldOf("visible", true).forGetter(null),
            Codec.BOOL.optionalFieldOf("mirror", false).forGetter(null)
    ).apply(instance, GeoCube::new));

    public String toString() {
        return "Cube[]";
    }

    public void appendModelData(String name, ModelPartData parent, Vec3f pivot) {
        parent.addChild(
                name,
                ModelPartBuilder.create()
                        .uv(uvOffset.x, uvOffset.y)
                        .mirrored(mirror)
                        .cuboid("cube",
                                origin.x - pivot.x,
                                -origin.y - pivot.y - size.y + 24,
                                origin.z - pivot.z,
                                size.x, size.y, size.z,
                                new Dilation(inflation)
                ),
                ModelTransform.NONE
        );
    }

}