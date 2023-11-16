package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.insomniacs.nucleus.impl.misc.NucleusCreativeModeTabs;
import net.minecraft.client.MinecraftClient;

public class NucleusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		NucleusItemPredicates.registerPredicates();
		boolean dev = FabricLoader.getInstance().isDevelopmentEnvironment();
//		boolean operatorItems = MinecraftClient.getInstance().options.getOperatorItemsTab().getValue();
		if (dev) { // && operatorItems) {
			NucleusCreativeModeTabs.init();
		}
	}

}