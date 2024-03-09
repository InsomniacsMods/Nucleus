package net.insomniacs.nucleus.api.geo_model;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.geo_model.data.GeoCube;
import net.insomniacs.nucleus.api.geo_model.data.GeoGroup;
import net.insomniacs.nucleus.api.geo_model.data.GeoTexture;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record GeoModelData(
    List<GeoGroup> groups,
    GeoTexture texture
) {

    @Nullable
    public static GeoModelData fromJson(@Nullable Identifier identifier, JsonObject object) {
        return CODEC.parse(JsonOps.INSTANCE, object).getOrThrow(true,
                message -> Nucleus.LOGGER.error(String.format("Error loading Model '%s': " + message, identifier))
        );
    }

    public TexturedModelData toModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        Map<String, GroupData> cachedGroupData = new HashMap<>();
        for (GeoGroup group : this.groups) {
            GroupData parentData = cachedGroupData.getOrDefault(group.parent(), new GroupData(root, null));
            ModelPartData groupData = group.toModelData(parentData.modelData, parentData.groupData);
            cachedGroupData.put(group.name(), new GroupData(groupData, group));
        }

        return TexturedModelData.of(data, this.texture.width, this.texture.height);
    }

    public static final Codec<GeoModelData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GeoGroup.CODEC.listOf().optionalFieldOf("bones", List.of()).forGetter(GeoModelData::groups),
            GeoTexture.CODEC.fieldOf("description").forGetter(GeoModelData::texture)
    ).apply(instance, GeoModelData::new));

    record GroupData (
            ModelPartData modelData,
            GeoGroup groupData
    ) {}

}
