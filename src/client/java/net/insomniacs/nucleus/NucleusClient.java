package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.insomniacs.nucleus.api.modreg.entries.ItemEntry;
import net.insomniacs.nucleus.impl.NucleusItemPredicates;
import net.minecraft.item.ItemStack;

public class NucleusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		NucleusItemPredicates.registerPredicates();

	}

}