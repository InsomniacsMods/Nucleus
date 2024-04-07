package net.insomniacs.nucleus.impl.modreg.datagen.generators;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.insomniacs.nucleus.api.modreg.entries.BlockEntry;
import net.insomniacs.nucleus.api.modreg.entries.ItemEntry;
import net.insomniacs.nucleus.api.modreg.utils.BlockModelEntry;
import net.insomniacs.nucleus.api.modreg.utils.ItemModelEntry;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.util.Identifier;

public class NucleusModelGenerator extends FabricModelProvider {

    public NucleusModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        ModRegistry.getEntries()
                .stream()
                .filter(entry -> entry.getType().equals(Nucleus.id("block")))
                .map(entry -> (BlockEntry)entry)
                .forEach(entry -> processBlock(generator, entry));
    }

    private void processBlock(BlockStateModelGenerator generator, BlockEntry entry) {
        BlockModelEntry model = entry.getModel();
        if (model.generateBlock) {
            Identifier identifier = model.parent.upload(entry.value(), model.textures, generator.modelCollector);
            var blockStates = BlockStateModelGenerator.createSingletonBlockState(entry.value(), identifier);
            generator.blockStateCollector.accept(blockStates);
        }
        if (model.generateItem) generator.registerParentedItemModel(entry.value(), entry.getId().withPrefixedPath("block/").withSuffixedPath(model.itemParentSuffix));
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        ModRegistry.getEntries()
                .stream()
                .filter(entry -> entry.getType().equals(Nucleus.id("item")))
                .map(entry -> (ItemEntry)entry)
                .forEach(entry -> processItem(generator, entry));
    }

    private void processItem(ItemModelGenerator generator, ItemEntry entry) {
        ItemModelEntry model = entry.getModel();
        if (model == null) return;
        generator.register(entry.value(), model.parent());
    }

}