package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.insomniacs.nucleus.api.components.ComponentKeys;
import net.insomniacs.nucleus.api.components.SanityComponent;
import net.insomniacs.nucleus.api.utils.BlockEntityHelper;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Logger;

public class Nucleus implements ModInitializer {

    public static final String API_ID = "nucleus";

    @Override
    public void onInitialize() {

    }

    /**
     * When provided a name it generates a ResourceLocation
     * @param name name of the path
     * @return ResourceLocation of mod with name as the path
     */
    public static Identifier getLocation(String name) {
        return new Identifier(API_ID,name);
    }
    public static Logger getDebugLogger() {
        return null;
    }

}
