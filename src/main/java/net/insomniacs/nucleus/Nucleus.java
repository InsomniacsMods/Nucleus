package net.insomniacs.nucleus;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.insomniacs.nucleus.impl.components.NucleusComponents;
import net.insomniacs.nucleus.impl.recipes.NucleusRecipes;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Nucleus implements ModInitializer {

    public static final String API_ID = "nucleus";
    public static final Logger LOGGER = LoggerFactory.getLogger("Nucleus");

    public static Identifier of(String name) {
        return Identifier.of(API_ID, name);
    }

    @Override
    public void onInitialize() {
        NucleusComponents.init();
        NucleusRecipes.init();
    }


    public static boolean isModLoaded(String mod) {
        return FabricLoader.getInstance().isModLoaded(mod);
    }

}
