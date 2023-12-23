package net.insomniacs.nucleus.utils;

import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

// TODO change this to be done properly, i just did the hackiest thing i could to make it *work*
public abstract class EarlyResourceReloadListener implements SimpleResourceReloadListener<Void> {

    public abstract void reload(ResourceManager manager);

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
