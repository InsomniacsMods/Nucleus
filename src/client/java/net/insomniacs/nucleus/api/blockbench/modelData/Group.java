package net.insomniacs.nucleus.api.blockbench.modelData;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.api.utils.Vec3i;
import net.minecraft.client.model.ModelPartBuilder;

import java.util.List;

public class Group extends Element {

    public List<String> elements;

    public Group(
            List<String> elements,
            String name, String uuid, Vec3i pivotPoint, Vec3i rotation, boolean visible, boolean mirror
    ) {
        this.elements = elements;

        this.name = name;
        this.uuid = uuid;
        this.pivotPoint = pivotPoint;
        this.rotation = rotation;
        this.visible = visible;
        this.mirror = mirror;
    }

    public String toString() {
        return "Group[" + this.uuid + "]";
    }

    public ModelPartBuilder modelData() {
        return null;
    }

    public static final Codec<Group> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.listOf().optionalFieldOf("children", List.of()).forGetter(null),

            Codec.STRING.optionalFieldOf("name", "cube").forGetter(null),
            Codec.STRING.fieldOf("uuid").forGetter(null),
            Vec3i.CODEC.optionalFieldOf("origin", Vec3i.NONE).forGetter(null),
            Vec3i.CODEC.optionalFieldOf("rotation", Vec3i.NONE).forGetter(null),
            Codec.BOOL.optionalFieldOf("visible", true).forGetter(null),
            Codec.BOOL.optionalFieldOf("mirror_uv", false).forGetter(null)
    ).apply(instance, Group::new));

}