package net.insomniacs.nucleus;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.insomniacs.nucleus.api.components.NucleusComponents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Nucleus implements ModInitializer {

    public static final String API_ID = "nucleus";

    public static final Logger LOGGER = LoggerFactory.getLogger("Nucleus");

    @Override
    public void onInitialize() {
        NucleusComponents.init();
    }

    /**
     * When provided a name it generates a ResourceLocation
     * @param name name of the path
     * @return ResourceLocation of mod with name as the path
     */
    public static Identifier getLocation(String name) {
        return new Identifier(API_ID,name);
    }

    public static Identifier of(String name) {
        return getLocation(name);
    }

    public static boolean isModLoaded(String mod) {
        return FabricLoader.getInstance().isModLoaded(mod);
    }

}
