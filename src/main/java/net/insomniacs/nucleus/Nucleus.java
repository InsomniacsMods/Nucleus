package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.insomniacs.nucleus.api.utils.BlockEntityHelper;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;

public class Nucleus implements ModInitializer {

    public static final String API_ID = "Nucleus";
    public static final String MANIC_ID = "manic";

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

}
