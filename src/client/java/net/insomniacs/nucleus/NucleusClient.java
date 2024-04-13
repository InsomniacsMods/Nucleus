package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.insomniacs.nucleus.impl.NucleusItemPredicates;
import net.insomniacs.nucleus.impl.splashTexts.SplashTextLoader;
import net.minecraft.resource.ResourceType;

public class NucleusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(SplashTextLoader.INSTANCE);
		NucleusItemPredicates.registerPredicates();

	}

}