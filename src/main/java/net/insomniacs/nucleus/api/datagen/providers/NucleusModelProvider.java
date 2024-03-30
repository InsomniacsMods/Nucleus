package net.insomniacs.nucleus.api.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class NucleusModelProvider extends FabricModelProvider {

    public static final BlockModelProvider BLOCK = new BlockModelProvider();
    public static final ItemModelProvider ITEM = new ItemModelProvider();

    public NucleusModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        BLOCK.consumers.forEach(consumer -> consumer.accept(generator));
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        ITEM.consumers.forEach(consumer -> consumer.accept(generator));
    }

}