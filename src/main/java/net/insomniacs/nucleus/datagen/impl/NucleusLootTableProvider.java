package net.insomniacs.nucleus.datagen.impl;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.insomniacs.nucleus.datagen.api.NucleusDataGenerator;
import net.insomniacs.nucleus.datagen.api.annotations.DatagenExempt;
import net.insomniacs.nucleus.datagen.api.annotations.Drops;
import net.insomniacs.nucleus.datagen.impl.utility.AnnotationUtils;
import net.insomniacs.nucleus.datagen.impl.utility.ProviderUtils;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

import static net.insomniacs.nucleus.datagen.impl.utility.AnnotationUtils.getAnnotation;

public class NucleusLootTableProvider extends FabricBlockLootTableProvider {

    private final NucleusDataGenerator dataGenerator;

    public NucleusLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup, NucleusDataGenerator dataGenerator) {
        super(dataOutput, registryLookup);
        this.dataGenerator = dataGenerator;
    }

    @Override
    public void generate() {
        ProviderUtils.streamRegistry(Registries.BLOCK, dataGenerator, (block) -> {
            var value = block.value();
            var blockClazz = value.getClass();

            if (isExempt(blockClazz)) return;

            var dropsAnnotation = getAnnotation(value, Drops.class);

            if (dropsAnnotation != null) {
                var droppedID = new Identifier(dropsAnnotation.item());
                var droppedItem = Registries.ITEM.get(droppedID);

                addDrop(value, drops(droppedItem));
            }
            else addDrop(value);
        });
    }

    boolean isExempt(Class<?> clazz) {
        return AnnotationUtils.isExempt(clazz, DatagenExempt.Exemption.LOOT_TABLE);
    }
}
