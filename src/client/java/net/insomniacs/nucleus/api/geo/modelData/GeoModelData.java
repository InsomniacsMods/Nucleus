package net.insomniacs.nucleus.api.geo.modelData;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.Nucleus;
import net.minecraft.client.model.*;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public record GeoModelData(
        List<Group> groups,
        Texture texture
) {

    @Nullable
    public static GeoModelData fromJson(@Nullable Identifier identifier, JsonObject object) {
        Consumer<String> logError = message -> Nucleus.LOGGER.error(String.format("Error loading Entity Model '%s': " + message, identifier));
        System.out.println(object);

//        if (!element.isJsonObject()) {
//            logError.accept("Model must be a JSON Object");
//            return null;
//        }

        try {
            return GeoModelData.CODEC.parse(JsonOps.INSTANCE, object).getOrThrow(true, logError);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public ModelPart toModelPart() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        Map<String, ModelPartData> cachedGroups = new HashMap<>();
        System.out.println("groups: " + this.groups);
        for (Group group : this.groups) {
            ModelPartData groupData = group.appendModelData(root, cachedGroups);
            cachedGroups.put(group.name, groupData);
        }
        return root.createPart(this.texture.width, this.texture.height);
    }

    public static final Codec<GeoModelData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Group.CODEC.listOf().fieldOf("bones").forGetter(GeoModelData::groups),
            Texture.CODEC.fieldOf("description").forGetter(GeoModelData::texture)
    ).apply(instance, GeoModelData::new));

}
