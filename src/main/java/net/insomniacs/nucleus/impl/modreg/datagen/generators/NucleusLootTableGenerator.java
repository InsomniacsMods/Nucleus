package net.insomniacs.nucleus.impl.modreg.datagen.generators;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.insomniacs.nucleus.api.modreg.entries.BlockEntry;
import net.insomniacs.nucleus.api.modreg.utils.BlockLootTableEntry;
import net.minecraft.item.ItemConvertible;

public class NucleusLootTableGenerator extends FabricBlockLootTableProvider {

    public NucleusLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        ModRegistry.getEntries()
                .stream()
                .filter(entry -> entry.getType().equals(Nucleus.id("block")))
                .map(entry -> (BlockEntry)entry)
                .forEach(this::processBlock);
    }

    private void processBlock(BlockEntry entry) {
        BlockLootTableEntry drops = entry.getDrops();
        if (drops == null) return;

        ItemConvertible item;
        if (drops.generateSelf && entry.getItem() != null) item = entry.getItem().value();
        else item = drops.item;
        System.out.println(item);
        addDrop(entry.value(), drops(item));
    }

}
