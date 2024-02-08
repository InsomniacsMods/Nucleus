package net.insomniacs.nucleus.test.client;

import net.fabricmc.api.ClientModInitializer;

public class NucleusTestClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        NucleusTestCreativeModeTabs.init();
    }

}
