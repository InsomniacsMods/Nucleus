package net.insomniacs.nucleus.test;

import net.fabricmc.api.ModInitializer;
import net.insomniacs.nucleus.api.modreg.ModRegistry;

public class NucleusTest implements ModInitializer {

    public static final ModRegistry REGISTRY = new ModRegistry("nucleus-test");

    @Override
    public void onInitialize() {
        NucleusTestItems.init();
        NucleusTestBlocks.init();
//        REGISTRY.register();
    }



}
