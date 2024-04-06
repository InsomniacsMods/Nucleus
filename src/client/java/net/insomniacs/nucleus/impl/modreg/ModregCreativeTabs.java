package net.insomniacs.nucleus.impl.modreg;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.insomniacs.nucleus.api.modreg.entries.ItemEntry;
import net.minecraft.item.ItemStack;

public class ModregCreativeTabs {

	public static void process() {
		ModRegistry.getEntries()
				.stream()
				.filter(entry -> entry.getType().equals(Nucleus.id("item")))
				.map(entry -> (ItemEntry)entry)
				.forEach(ModregCreativeTabs::processItem);
	}

	private static void processItem(ItemEntry entry) {
		ItemStack stack = entry.value().getDefaultStack();
		entry.getCreativeTabs().forEach(tab -> {
			ItemGroupEvents.modifyEntriesEvent(tab.group()).register(Nucleus.id("datagen"), content -> {
				if (tab.after() == null) content.add(stack);
				else content.addAfter(tab.after(), stack);
			});
		});
	}

}
