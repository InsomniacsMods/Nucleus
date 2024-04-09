package net.insomniacs.nucleus.impl.modreg.datagen.generators.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.insomniacs.nucleus.api.modreg.entries.ItemEntry;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class NucleusItemTagGenerator extends FabricTagProvider.ItemTagProvider {

	public NucleusItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
		super(output, completableFuture, null);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup lookup) {
		ModRegistry.<ItemEntry>getEntries("item").forEach(this::processItem);
	}

	private void processItem(ItemEntry entry) {
		entry.getTags().forEach(tag ->
				getOrCreateTagBuilder(tag).add(entry.value())
		);
	}

}
