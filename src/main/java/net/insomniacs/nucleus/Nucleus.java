package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Nucleus implements ClientModInitializer, ModInitializer {

    public static final String API_ID = "Nucleus";

    @Override
    public void onInitialize() {

    }

    @Override
    public void onInitializeClient() {

    }

    /**
     * When provided a name it generates a ResourceLocation
     * @param name name of the path
     * @return ResourceLocation of mod with name as the path
     */
    public static Identifier getLocation(String name) {
        return new Identifier(API_ID,name);
    }

}
