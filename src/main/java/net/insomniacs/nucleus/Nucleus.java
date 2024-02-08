package net.insomniacs.nucleus;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Logger;

public class Nucleus implements ModInitializer {

    public static final String API_ID = "nucleus";

    @Override
    public void onInitialize() {}

    /**
     * When provided a name it generates a ResourceLocation
     * @param name name of the path
     * @return ResourceLocation of mod with name as the path
     */
    public static Identifier getLocation(String name) {
        return new Identifier(API_ID,name);
    }

    public static Identifier id(String name) {
        return getLocation(name);
    }

    public static Logger getDebugLogger() {
        return null;
    }

}
