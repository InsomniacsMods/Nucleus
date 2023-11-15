package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;

public class NucleusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		NucleusItemPredicates.registerPredicates();
	}

}