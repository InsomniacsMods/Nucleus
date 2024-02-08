package net.insomniacs.nucleus.api.geo_model.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.mixin.client.ModelPartDataAccessor;
import net.insomniacs.nucleus.utils.Vec3r;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class GeoGroup {

    private final String name;
    private final String parent;
    private final List<GeoCube> cubes;

    private final Vec3d pivot;
    private final Vec3r rotation;
    private final boolean visible;

    public GeoGroup(
            String name, String parent, List<GeoCube> cubes,
            Vec3d pivot, Vec3r rotation, boolean visible
    ) {
        this.name = name;
        this.parent = parent;
        this.cubes = cubes;

        this.pivot = new Vec3d(
                pivot.x,
                // TODO make this smaller? .add(0, -24, 0) ?
                24 - pivot.y,
                pivot.z
        );
        this.rotation = rotation;
        this.visible = visible;
    }


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

    public ModelPartData createModelData(ModelPartData parent, GeoGroup parentGroup) {

        ModelPartData modelData = createModelData(parent, ModelPartBuilder.create(), parentGroup);

        for (int i = 0; i < cubes.size(); i++) {
            GeoCube cube = cubes.get(i);
            System.out.println("cube name: " + (name+i));
            modelData.addChild(
                    name+i,
                    cube.partBuilder(pivot),
                    cube.partTransform(pivot)
            );
        }

        return modelData;

    }

    public ModelPartData createModelData(ModelPartData parent, ModelPartBuilder builder, GeoGroup parentGroup) {
        Vec3d pivot = this.pivot;
        if (parentGroup != null) pivot = pivot.add(parentGroup.pivot);
        return parent.addChild(
                name,
                builder,
                ModelTransform.of(
                        (float)pivot.x, (float)pivot.y, (float)pivot.z,
                        (float)rotation.x, (float)rotation.y, (float)rotation.z
                )
        );
    }

}