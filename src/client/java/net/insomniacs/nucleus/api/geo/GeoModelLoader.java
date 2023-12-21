package net.insomniacs.nucleus.api.geo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.insomniacs.nucleus.Nucleus;
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

// TODO change this to be done properly, i just did the hackiest thing i could to make it *work*
public class GeoModelLoader implements SimpleResourceReloadListener<Void> {

    public static TexturedModelData get(Identifier identifier) {
        return MODELS.get(identifier);
    }

    public static TexturedModelData getEntity(Identifier entityID) {
        return get(entityID.withPrefixedPath("entity/"));
    }


    public static Map<Identifier, TexturedModelData> MODELS = new HashMap<>();

    public static GeoModelLoader INSTANCE = new GeoModelLoader();

    private GeoModelLoader() {}

    @Override
    public Identifier getFabricId() {
        return Nucleus.id("blockbench_entity_models");
    }

    private static final String MODELS_DIRECTORY = "models";
    private static final String FILE_EXTENSION = ".geo.json";


    public void reload(ResourceManager manager) {
        MODELS.clear();
        Map<Identifier, Resource> textReader = manager.findResources(MODELS_DIRECTORY, r -> r.getPath().endsWith(FILE_EXTENSION));

        for (Identifier identifier : textReader.keySet()) {
            try {
                BufferedReader fileReader = textReader.get(identifier).getReader();
                registerModel(identifier, fileReader);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void registerModel(Identifier identifier, BufferedReader reader) {
        String newPath = identifier.getPath().split(MODELS_DIRECTORY+"/")[1].split(FILE_EXTENSION)[0];
        identifier = identifier.withPath(newPath);
        JsonElement element = JsonParser.parseReader(reader);
        JsonObject object = element.getAsJsonObject().getAsJsonArray("minecraft:geometry").get(0).getAsJsonObject();

        GeoModelData model = GeoModelData.fromJson(identifier, object);
        if (model == null) return;

        MODELS.put(identifier, model.toModelPart());
    }

    @Override
    public CompletableFuture<Void> load(ResourceManager manager, Profiler profiler, Executor executor) {
        reload(manager);
        return CompletableFuture.supplyAsync(() -> null, executor);
    }

    @Override
    public CompletableFuture<Void> apply(Void data, ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> {}, executor);
    }

}
