package net.insomniacs.nucleus.datagen.impl;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.insomniacs.nucleus.datagen.api.Datagen;
import net.insomniacs.nucleus.datagen.api.annotations.DatagenExempt;
import net.insomniacs.nucleus.datagen.api.annotations.Drops;
import net.insomniacs.nucleus.datagen.impl.utility.AnnotationUtils;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static net.insomniacs.nucleus.datagen.impl.utility.AnnotationUtils.getAnnotation;

public class NucleusLootTableProvider extends FabricBlockLootTableProvider {

    private final Map<Registry<?>, Datagen.RefAnnotationPair[]> refMap;

    public NucleusLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup, Map<Registry<?>, Datagen.RefAnnotationPair[]> refMap) {
        super(dataOutput, registryLookup);
        this.refMap = refMap;
    }

    @Override
    public void generate() {
        Arrays.stream(refMap.get(Registries.BLOCK)).forEach(pair -> {
            var ref = pair.reference();
            var block = (Block) ref.value();
            var blockClazz = block.getClass();

            if (isExempt(blockClazz)) return;

            var dropsAnnotation = getAnnotation(blockClazz, Drops.class);

            dropsAnnotation.ifPresentOrElse(drop -> {
                var droppedID = Identifier.of(drop.item());
                var droppedItem = Registries.ITEM.get(droppedID);

                addDrop(block, drops(droppedItem));
            }, () -> addDrop(block));
        });
    }

    boolean isExempt(Class<?> clazz) {
        return AnnotationUtils.isExempt(clazz, DatagenExempt.Exemption.LOOT_TABLE);
    }
}
