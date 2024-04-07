package net.insomniacs.nucleus.impl.modreg.datagen.generators;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public abstract class NucleusTagGenerator<T> extends FabricTagProvider<T> {

    protected abstract RegistryKey<Registry<T>> registryKey();

    public NucleusTagGenerator(FabricDataOutput output, RegistryKey<Registry<T>> registryKey, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registryKey, registriesFuture);
    }

}
