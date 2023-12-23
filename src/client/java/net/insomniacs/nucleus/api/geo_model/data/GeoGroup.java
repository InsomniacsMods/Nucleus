package net.insomniacs.nucleus.api.geo_model.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.mixin.client.ModelPartDataAccessor;
import net.insomniacs.nucleus.utils.Vec3f;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;

import java.util.List;
import java.util.Map;

public record GeoGroup(
        String name, String parent, List<GeoCube> cubes,
        Vec3f pivot, Vec3f rotation, boolean visible
) {

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public static final Codec<GeoGroup> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(null),
            Codec.STRING.optionalFieldOf("parent", "").forGetter(null),
            GeoCube.CODEC.listOf().optionalFieldOf("cubes", List.of()).forGetter(null),

            Vec3f.CODEC.optionalFieldOf("pivot", Vec3f.NONE).forGetter(null),
            Vec3f.CODEC.optionalFieldOf("rotation", Vec3f.NONE).forGetter(null),
            Codec.BOOL.optionalFieldOf("visible", true).forGetter(null)
    ).apply(instance, GeoGroup::new));

    public ModelPartData appendModelData(ModelPartData parent) {
        ModelTransform parentTransform = ((ModelPartDataAccessor)parent).getRotationData();

        Vec3f newPivot = new Vec3f(
                pivot.x - parentTransform.pivotX,
                pivot.y - parentTransform.pivotY,
                pivot.z - parentTransform.pivotZ
        );
        Vec3f newRotation = new Vec3f(
                rotation.x - parentTransform.pitch,
                rotation.y - parentTransform.yaw,
                rotation.z - parentTransform.roll
        );

        ModelPartData data = parent.addChild(
                this.name,
                ModelPartBuilder.create(),
                ModelTransform.of(
                        newPivot.x, newPivot.y, newPivot.z,
                        newRotation.x, newRotation.y, newRotation.z
                )
        );
        for (int i = 0; i < this.cubes.size(); i++) {
            GeoCube cube = this.cubes.get(i);
            cube.appendModelData(this.name+i, data, pivot);
        }
        return data;
    }

}