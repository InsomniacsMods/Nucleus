package net.insomniacs.nucleus.test;

import net.fabricmc.api.ModInitializer;

public class NucleusTest implements ModInitializer {

    @Override
    public void onInitialize() {
        NucleusTestItems.init();
        NucleusTestBlocks.init();
    }

}
