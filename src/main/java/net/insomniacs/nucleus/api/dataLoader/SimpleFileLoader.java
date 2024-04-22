package net.insomniacs.nucleus.api.dataLoader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;

import java.io.BufferedReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

public interface SimpleFileLoader extends SimpleResourceReloadListener<Void> {

    void init(DataFileLoader loader, ResourceManager manager);

    default void load(ResourceManager manager) {
        DataFileLoader loader = new DataFileLoader();
        init(loader, manager);
        loader.load(manager);
    }

    class DataFileLoader {

        public FileReader<JsonElement> JSON;
        public FileReader<Stream<String>> RAW;

        protected DataFileLoader() {
            JSON = new FileReader<>(JsonParser::parseReader);
            RAW = new FileReader<>(BufferedReader::lines);
        }

        public FileReader<JsonElement> json() {
            return JSON;
        }

        public FileReader<Stream<String>> raw() {
            return RAW;
        }

        protected void load(ResourceManager manager) {
            JSON.load(manager);
            RAW.load(manager);
        }

    }


    @Override
    default CompletableFuture<Void> load(ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.runAsync( () -> load(manager), executor );
    }

    @Override
    default CompletableFuture<Void> apply(Void data, ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.runAsync( () -> {}, executor );
    }

}
