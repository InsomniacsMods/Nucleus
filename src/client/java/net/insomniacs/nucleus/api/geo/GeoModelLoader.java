package net.insomniacs.nucleus.api.geo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.geo.modelData.GeoModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

// TODO change this to be done properly, i just did the hackiest thing i could to make it *work*
public class GeoModelLoader implements SimpleResourceReloadListener<Void> {

    public static Registry<ModelPart> MODELS = new SimpleRegistry<>(RegistryKey.ofRegistry(Nucleus.id("entity_models")), Lifecycle.stable());

    public static GeoModelLoader INSTANCE = new GeoModelLoader();

    private GeoModelLoader() {}

    @Override
    public Identifier getFabricId() {
        return Nucleus.id("blockbench_entity_models");
    }

    private static final String MODELS_DIRECTORY = "models/entity";
    private static final String FILE_EXTENSION = ".geo.json";



    public void reload(ResourceManager manager) {
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

        Registry.register(MODELS, identifier, model.toModelPart());
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
