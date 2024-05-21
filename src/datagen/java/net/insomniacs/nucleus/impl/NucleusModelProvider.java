package net.insomniacs.nucleus.impl;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.insomniacs.nucleus.api.NucleusDataGenerator;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.registry.Registries;

import static net.insomniacs.nucleus.impl.utility.ProviderUtils.streamAllRegistries;

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
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        streamAllRegistries(generator, ((registry, registryEntry) -> {
            if (registry.equals(Registries.ITEM)) {

            }
        }));
    }
}
