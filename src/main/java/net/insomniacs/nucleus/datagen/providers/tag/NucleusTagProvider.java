package net.insomniacs.nucleus.datagen.providers.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.insomniacs.nucleus.api.registries.Registries;
import net.minecraft.data.server.tag.TagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class NucleusTagProvider extends TagProvider<Object> {

    public NucleusTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, Registries.getKey("any"), registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
//        ModRegistry.ENTRIES.forEach(this::processEntry);
    }

//    @SuppressWarnings("unchecked")
//    private void processEntry(ModEntry<?, ?> entry) {
//        entry.tags.forEach(tag -> getOrCreateTagBuilder((TagKey<Object>)tag).add(entry.id()));
//    }

}
