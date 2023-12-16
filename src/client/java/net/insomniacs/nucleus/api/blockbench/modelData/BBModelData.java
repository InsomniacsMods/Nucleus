package net.insomniacs.nucleus.api.blockbench.modelData;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.Nucleus;
import net.minecraft.client.model.*;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public record BBModelData(
        List<Cube> cubes,
        List<Group> groups,
        List<Texture> textures,
        Resolution resolution
) {

    public static class Texture {

        public Identifier location;

        public Texture(String namespace, String folder, String name) {
            this.location = new Identifier(namespace, folder + "/" + name);
        }

        public static final Codec<Texture> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("namespace").forGetter(null),
                Codec.STRING.fieldOf("folder").forGetter(null),
                Codec.STRING.fieldOf("name").forGetter(null)
        ).apply(instance, Texture::new));

    }

    public record Resolution(int width, int height) {
        public static final Codec<Resolution> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("width").forGetter(null),
                Codec.INT.fieldOf("height").forGetter(null)
        ).apply(instance, Resolution::new));
    }

    public static boolean isBoxUV(JsonObject object) {
        if (!object.has("meta")) return false;
        JsonObject meta = object.get("meta").getAsJsonObject();

        if (!meta.has("box_uv")) return false;
        return meta.get("box_uv").getAsBoolean();
    }

    @Nullable
    public static BBModelData fromJson(@Nullable Identifier identifier, JsonElement element) {
        Consumer<String> logError = message -> Nucleus.LOGGER.error(String.format("Error loading Entity Model '%s': " + message, identifier));

        if (!element.isJsonObject()) {
            logError.accept("Model must be a JSON Object");
            return null;
        }

        JsonObject object = element.getAsJsonObject();

        if (!isBoxUV(object)) {
            logError.accept("Model must have Box UV enabled");
            return null;
        }

        try {
            return BBModelData.CODEC.parse(JsonOps.INSTANCE, object).getOrThrow(true, logError);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public ModelPart toModelPart() {
        ModelData result = new ModelData();
        ModelPartData root = result.getRoot();
        for (Cube cube : this.cubes) {
            root.addChild(cube.name, cube.modelData(), cube.modelTransformation());
        }

        return result.getRoot().createPart(this.resolution.width, this.resolution.height);
    }

    public static final Codec<BBModelData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Cube.CODEC.listOf().fieldOf("elements").forGetter(BBModelData::cubes),
            Group.CODEC.listOf().fieldOf("outliner").forGetter(BBModelData::groups),
            Texture.CODEC.listOf().fieldOf("textures").forGetter(BBModelData::textures),
            Resolution.CODEC.fieldOf("resolution").forGetter(BBModelData::resolution)
    ).apply(instance, BBModelData::new));

}
