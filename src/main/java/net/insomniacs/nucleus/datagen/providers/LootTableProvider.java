package net.insomniacs.nucleus.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.insomniacs.nucleus.api.modreg.entries.BlockEntry;

public class LootTableProvider extends FabricBlockLootTableProvider {

    protected LootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        ModRegistry.ENTRIES
                .stream()
                .filter(entry -> entry.getType().equals(Nucleus.id("block")))
                .map(entry -> (BlockEntry)entry)
                .forEach(this::processBlock);
    }

    private void processBlock(BlockEntry entry) {

    }

}
