package net.insomniacs.nucleus.api.blockbench;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.blockbench.modelData.BBModelData;
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
public class BBEntityDataLoader implements SimpleResourceReloadListener<Void> {

    public static Registry<ModelPart> MODELS = new SimpleRegistry<>(RegistryKey.ofRegistry(Nucleus.id("entity_models")), Lifecycle.stable());

    public static BBEntityDataLoader INSTANCE = new BBEntityDataLoader();

    private BBEntityDataLoader() {}

    private void clearRegistry() {
        MODELS = new SimpleRegistry<>(RegistryKey.ofRegistry(Nucleus.id("entity_models")), Lifecycle.stable());
    }


    @Override
    public Identifier getFabricId() {
        return Nucleus.id("blockbench_entity_models");
    }

    private static final String MODELS_DIRECTORY = "models/entity";



    public void reload(ResourceManager manager) {
        clearRegistry();

        Map<Identifier, Resource> textReader = manager.findResources(MODELS_DIRECTORY, r -> r.getPath().endsWith(".bbmodel"));

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
        String newPath = identifier.getPath().split("models/entity/")[1].split(".bbmodel")[0];
        identifier = identifier.withPath(newPath);
        JsonElement object = JsonParser.parseReader(reader);

        BBModelData model = BBModelData.fromJson(identifier, object);
        if (model == null) return;

        System.out.println(identifier);
        System.out.println(model.groups().get(0).elements);
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
