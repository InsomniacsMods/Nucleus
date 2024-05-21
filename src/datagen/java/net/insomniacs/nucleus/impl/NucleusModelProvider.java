package net.insomniacs.nucleus.impl;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.insomniacs.nucleus.api.NucleusDataGenerator;
import net.insomniacs.nucleus.impl.utility.ProviderUtils;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

//@ItemModel(model = "CUBE")
public class NucleusModelProvider extends FabricModelProvider {

    private final NucleusDataGenerator generator;

    public NucleusModelProvider(FabricDataOutput output, NucleusDataGenerator generator) {
        super(output);
        this.generator = generator;
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator modelGenerator) {
        ProviderUtils.streamAllRegistries(generator, (registry, entry) -> NucleusItemModelProvider.generateItemModel(modelGenerator, registry, entry));
    }

}
