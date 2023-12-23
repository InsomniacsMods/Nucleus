package net.insomniacs.nucleus.api.geo_model;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.geo_model.data.GeoGroup;
import net.insomniacs.nucleus.api.geo_model.data.GeoTexture;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public record GeoModelData(
    List<GeoGroup> groups,
    GeoTexture texture
) {

    @Nullable
    public static GeoModelData fromJson(@Nullable Identifier identifier, JsonObject object) {
        Consumer<String> logError = message -> Nucleus.LOGGER.error(String.format("Error loading Model '%s': " + message, identifier));
        return CODEC.parse(JsonOps.INSTANCE, object).getOrThrow(true, logError);
    }

    public TexturedModelData toModelPart() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        Map<String, ModelPartData> cachedGroups = new HashMap<>();
        for (GeoGroup group : this.groups) {
            ModelPartData parent;
            if (group.getParent().isEmpty()) parent = root;
            else parent = cachedGroups.get(group.getParent());
            ModelPartData groupData = group.appendModelData(parent);
            cachedGroups.put(group.getName(), groupData);
        }
        return TexturedModelData.of(data, this.texture.width, this.texture.height);
    }

    public static final Codec<GeoModelData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GeoGroup.CODEC.listOf().fieldOf("bones").forGetter(GeoModelData::groups),
            GeoTexture.CODEC.fieldOf("description").forGetter(GeoModelData::texture)
    ).apply(instance, GeoModelData::new));

}
