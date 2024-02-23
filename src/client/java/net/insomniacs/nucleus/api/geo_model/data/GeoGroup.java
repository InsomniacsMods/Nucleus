package net.insomniacs.nucleus.api.geo_model.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.utils.Vec3r;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public record GeoGroup(
        String name, String parent, List<GeoCube> cubes,
        Vec3d pivot, Vec3r rotation, boolean visible
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

            Vec3d.CODEC.optionalFieldOf("pivot", Vec3d.ZERO).forGetter(null),
            Vec3r.CODEC.optionalFieldOf("rotation", Vec3r.ZERO).forGetter(null),
            Codec.BOOL.optionalFieldOf("visible", true).forGetter(null)
    ).apply(instance, GeoGroup::new));

    public ModelPartData toModelData(ModelPartData parent, GeoGroup parentGroup) {
        return parent.addChild(
                name,
                ModelPartBuilder.create(),
                getTransformation(parentGroup)
        );
    }

    public ModelTransform getTransformation(GeoGroup parent) {
        Vec3d pivot = this.pivot;
        if (parent != null) pivot = pivot.subtract(parent.pivot);
        pivot.multiply(1, -1, 1);
//        pivot.add(0, 24, 0);

        return ModelTransform.of(
                (float)pivot.x, (float)pivot.y, (float)pivot.z,
                (float)rotation.x, (float)rotation.y, (float)rotation.z
        );
    }

    public void addChildren(ModelPartData modelData) {
        for (int i = 0; i < cubes.size(); i++) {
            GeoCube cube = cubes.get(i);
            cube.append(modelData, name+i);
        }
    }

}