package net.insomniacs.golgi;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Golgi implements ModInitializer {

    public static final String MOD_ID = "golgi";
    public static final Logger LOGGER = LoggerFactory.getLogger("Golgi");

    public static Identifier of(String name) {
        return new Identifier(MOD_ID, name);
    }

    @Override
    public void onInitialize() {
        GolgiItems.init();
        GolgiBlocks.init();
        GolgiRecipeTypes.init();
    }

}
