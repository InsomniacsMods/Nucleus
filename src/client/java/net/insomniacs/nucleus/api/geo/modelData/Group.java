package net.insomniacs.nucleus.api.geo.modelData;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.api.util.Vec3i;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;

import java.util.List;
import java.util.Map;

public class Group extends Element {

    public String name;
    public String parent;
    public List<Cube> cubes;

    public Group(
            String name, String parent, List<Cube> cubes,
            Vec3i pivot, Vec3i rotation
    ) {
        this.name = name;
        this.parent = parent;
        this.cubes = cubes;

        this.pivot = pivot;
        this.rotation = rotation;
    }

    public static final Codec<Group> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(null),
            Codec.STRING.optionalFieldOf("parent", "").forGetter(null),
            Cube.CODEC.listOf().optionalFieldOf("cubes", List.of()).forGetter(null),
            Vec3i.CODEC.optionalFieldOf("pivot", Vec3i.NONE).forGetter(null),
            Vec3i.CODEC.optionalFieldOf("rotation", Vec3i.NONE).forGetter(null)
    ).apply(instance, Group::new));

    public ModelPartData appendModelData(ModelPartData parent, Map<String, ModelPartData> cache) {
        if (cache.containsKey(this.parent)) parent = cache.get(this.parent);
        ModelPartData data = parent.addChild(
                this.name,
                ModelPartBuilder.create(),
//                        .cuboid(0, 0, 0, 0, 0, 0),
                ModelTransform.of(
                        0, 0, 0,
//                        this.pivot.x, this.pivot.y, this.pivot.z,
                        this.rotation.x, this.rotation.y, this.rotation.z
                )
        );
        this.cubes.forEach(cube -> cube.appendModelData(data));
        return data;
    }

}