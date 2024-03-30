package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;
import net.insomniacs.nucleus.impl.NucleusItemPredicates;

public class NucleusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		NucleusItemPredicates.registerPredicates();
	}

}