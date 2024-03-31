package net.insomniacs.nucleus.datagen.providers.model;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.insomniacs.nucleus.api.modreg.entries.BlockEntry;
import net.insomniacs.nucleus.api.modreg.entries.ItemEntry;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class NucleusModelProvider extends FabricModelProvider {

    public NucleusModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        ModRegistry.ENTRIES
                .stream()
                .filter(entry -> entry.getType().equals(Nucleus.id("block")))
                .map(entry -> (BlockEntry)entry)
                .forEach(entry -> processBlock(generator, entry));
    }

    private void processBlock(BlockStateModelGenerator generator, BlockEntry entry) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        ModRegistry.ENTRIES
                .stream()
                .filter(entry -> entry.getType().equals(Nucleus.id("item")))
                .map(entry -> (ItemEntry)entry)
                .forEach(entry -> processItem(generator, entry));
    }

    private void processItem(ItemModelGenerator generator, ItemEntry entry) {
        if (entry.generateModel) generator.register(entry.value(), entry.parentModel);
    }

}