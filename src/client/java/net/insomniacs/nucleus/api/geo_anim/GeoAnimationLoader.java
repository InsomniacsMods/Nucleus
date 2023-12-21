package net.insomniacs.nucleus.api.geo_anim;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.utils.FiletypeResourceReloadListener;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GeoAnimationLoader extends FiletypeResourceReloadListener {

    @Override
    public Identifier getFabricId() {
        return Nucleus.id("geo_animations");
    }

    @Override
    public String getDirectory() {
        return "animations";
    }

    @Override
    public String getFileExtension() {
        return ".animation.json";
    }


    public static GeoAnimationLoader INSTANCE = new GeoAnimationLoader();

    protected GeoAnimationLoader() {}


    public static Map<Identifier, Map<String, Animation>> ANIMATIONS = new HashMap<>();

    public static Map<String, Animation> get(Identifier identifier) {
        return ANIMATIONS.get(identifier);
    }

    public static Map<String, Animation> getEntity(Identifier entityID) {
        return get(entityID.withPrefixedPath("entity/"));
    }

    public static Map<String, Animation> getBlock(Identifier entityID) {
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
        JsonObject object = JsonParser.parseReader(reader).getAsJsonObject();

        GeoAnimationData animationData = GeoAnimationData.fromJson(path, object);
        if (animationData == null) return;

        ANIMATIONS.put(path, animationData.getAnimationMap());
    }

}
