package net.insomniacs.nucleus.api.geo_model.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.utils.Vec3r;
import net.minecraft.client.model.*;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public record GeoGroup (
        String name, String parent, List<GeoCube> cubes,
        Vec3d pivot, Vec3r rotation, boolean visible
) {

    public static final Codec<GeoGroup> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(null),
            Codec.STRING.optionalFieldOf("root", "").forGetter(null),
            GeoCube.CODEC.listOf().optionalFieldOf("cubes", List.of()).forGetter(null),

            Vec3d.CODEC.optionalFieldOf("pivot", Vec3d.ZERO).forGetter(null),
            Vec3r.CODEC.optionalFieldOf("rotation", Vec3r.ZERO).forGetter(null),
            Codec.BOOL.optionalFieldOf("visible", true).forGetter(null)
    ).apply(instance, GeoGroup::new));

    public ModelPartData toModelData(ModelPartData root, GeoGroup parentGroup) {
        ModelPartBuilder part = ModelPartBuilder.create();

        ArrayList<GeoCube> simpleCubes = new ArrayList<>(), uniqueCubes = new ArrayList<>();
        cubes.forEach(cube -> {
            if (cube.isSimple()) simpleCubes.add(cube);
            else uniqueCubes.add(cube);
        });

        for (GeoCube cube : simpleCubes) {
            part = cube.addToBuilder(part, pivot);
        }

        Vec3d pivot = this.pivot;
        if (parentGroup != null) pivot = pivot.subtract(parentGroup.pivot);
        pivot.multiply(-1, -1, 1);
        pivot.add(0, 24, 0);

        ModelTransform transform = ModelTransform.of(
                (float)pivot.x, (float)pivot.y, (float)pivot.z,
                (float)rotation.x, (float)rotation.y, (float)rotation.z
        );

        ModelPartData data = root.addChild(name, part, transform);

        var i = 0;
        for (GeoCube cube : uniqueCubes) {
            i++;
            ModelPartBuilder uniquePart = ModelPartBuilder.create();
            uniquePart = cube.addToBuilder(uniquePart, pivot);
            ModelTransform uniqueTransform = cube.getTransformation();
            data.addChild(name+i, uniquePart, uniqueTransform);
        }

        return data;
    }


}