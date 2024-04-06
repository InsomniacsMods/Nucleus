package net.insomniacs.nucleus.impl.modreg.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.insomniacs.nucleus.api.modreg.ModEntry;
import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.insomniacs.nucleus.api.registries.Registries;
import net.minecraft.data.server.tag.TagProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public abstract class NucleusTagProvider<T> extends TagProvider<T> {

    private Identifier type;

    public NucleusTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, Identifier type) {
        super(output, RegistryKey.ofRegistry(type), registriesFuture);
        this.type = type;
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        ModRegistry.getEntries()
                .stream()
                .filter(entry -> entry.getType().equals(type))
                .forEach(this::processEntry);
    }

    @SuppressWarnings("unchecked")
    private void processEntry(ModEntry<?,?,?,?> entry) {
        entry.tags
                .stream()
                .map(tag -> (TagKey<T>)tag)
                .forEach(tag -> getOrCreateTagBuilder(tag).add());
        entry.tags.forEach(
                tag -> getOrCreateTagBuilder((TagKey<T>)tag).addOptional(entry.getId())
        );
    }

}
