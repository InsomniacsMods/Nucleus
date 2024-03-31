package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.insomniacs.nucleus.api.modreg.entries.ItemEntry;
import net.minecraft.item.ItemStack;

public class NucleusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
//		NucleusItemPredicates.registerPredicates();

		System.out.println("AAAAAAAAAAAAAAAAAAA");
		System.out.println(ModRegistry.ENTRIES);

		ModRegistry.ENTRIES
				.stream()
				.filter(entry -> entry.getType().equals(Nucleus.id("item")))
				.map(entry -> (ItemEntry)entry)
				.forEach(this::processItem);
	}

	private void processItem(ItemEntry entry) {
		ItemStack stack = entry.value().getDefaultStack();
		entry.creativeTabs.forEach(tab -> {
			ItemGroupEvents.modifyEntriesEvent(tab.group()).register(Nucleus.id("datagen"), content -> {
				if (tab.after() == null) content.add(stack);
				else content.addAfter(tab.after(), stack);
			});
		});
	}

}