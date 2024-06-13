package net.insomniacs.nucleus.datagen.impl;

import net.insomniacs.nucleus.datagen.api.NucleusDataGenerator;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class NucleusRecipeProvider extends RecipeProvider {

    private final NucleusDataGenerator dataGenerator;

    public NucleusRecipeProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture, NucleusDataGenerator generator) {
        super(output, registryLookupFuture);

        this.dataGenerator = generator;
    }

    @Override
    public void generate(RecipeExporter exporter) {

    }
}
