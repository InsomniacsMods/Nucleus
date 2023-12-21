package net.insomniacs.nucleus.api.geo_model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.geo_anim.GeoAnimationData;
import net.insomniacs.nucleus.api.geo_anim.GeoAnimationLoader;
import net.insomniacs.nucleus.utils.FiletypeResourceReloadListener;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class GeoModelLoader extends FiletypeResourceReloadListener {

    @Override
    public Identifier getFabricId() {
        return Nucleus.id("geo_models");
    }

    @Override
    public String getDirectory() {
        return "models";
    }

    @Override
    public String getFileExtension() {
        return ".geo.json";
    }


    public static GeoModelLoader INSTANCE = new GeoModelLoader();

    protected GeoModelLoader() {}


    public static Map<Identifier, TexturedModelData> MODELS = new HashMap<>();

    public static TexturedModelData get(Identifier identifier) {
        return MODELS.get(identifier);
    }

    public static TexturedModelData getEntity(Identifier entityID) {
        return get(entityID.withPrefixedPath("entity/"));
    }

    public static TexturedModelData getBlock(Identifier entityID) {
        return get(entityID.withPrefixedPath("block/"));
    }


    @Override
    public void processFiles(Map<Identifier, Resource> files) {
        files.forEach((path, resource) -> {
            try {
                BufferedReader reader = resource.getReader();
                registerModel(path, reader);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void registerModel(Identifier path, BufferedReader reader) {
        String newPath = path.getPath().split(getDirectory()+"/")[1].split(getFileExtension())[0];
        path = path.withPath(newPath);
        JsonElement element = JsonParser.parseReader(reader);
        JsonObject object = element.getAsJsonObject().getAsJsonArray("minecraft:geometry").get(0).getAsJsonObject();

        GeoModelData model = GeoModelData.fromJson(path, object);
        if (model == null) return;

        MODELS.put(path, model.toModelPart());
    }

}
