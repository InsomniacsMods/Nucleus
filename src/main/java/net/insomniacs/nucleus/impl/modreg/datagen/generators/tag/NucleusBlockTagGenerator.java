package net.insomniacs.nucleus.impl.modreg.datagen.generators.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.insomniacs.nucleus.api.modreg.entries.BlockEntry;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class NucleusBlockTagGenerator extends FabricTagProvider.BlockTagProvider {

	public NucleusBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup lookup) {
		ModRegistry.<BlockEntry>getEntries("block").forEach(this::processBlock);
	}

	private void processBlock(BlockEntry entry) {
		entry.getTags().forEach(tag ->
				getOrCreateTagBuilder(tag).add(entry.value())
		);
	}

}
