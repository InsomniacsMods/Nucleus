package net.insomniacs.nucleus.impl;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.insomniacs.nucleus.api.NucleusDataGenerator;
import net.insomniacs.nucleus.api.annotations.ModelOverride;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;

import java.util.Objects;
import java.util.logging.Logger;

import static net.insomniacs.nucleus.impl.utility.AnnotationUtils.getAnnotation;
import static net.insomniacs.nucleus.impl.utility.ProviderUtils.streamAllRegistries;

@ModelOverride(model = "CUBE")
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
            if (!registry.equals(Registries.ITEM)) return;
            var value = (Item) registryEntry.value(); // We know it at minimum extends item.
            var itemClazz = value.getClass();

            var modelAnnotation = getAnnotation(itemClazz, ModelOverride.class);
            var model = Models.HANDHELD;

            if (value instanceof BlockItem)
                model = Models.GENERATED;

            if (isToolItem(value))
                model = Models.HANDHELD;

            if (modelAnnotation != null) try {
                var modelsClazz = modelAnnotation.modelHome();
                var modelField = modelsClazz.getField(modelAnnotation.model());

                modelField.setAccessible(true);
                model = (Model) modelField.get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                LOGGER.info("Model: " + modelAnnotation.model() + " doesn't exist.");
            }

            itemModelGenerator.register(value, model);
        }));
    }

    boolean isToolItem(Item item) {
        var stack = item.getDefaultStack();
        return stack.isIn(ItemTags.AXES) ||
                stack.isIn(ItemTags.PICKAXES) ||
                stack.isIn(ItemTags.SWORDS) ||
                stack.isIn(ItemTags.HOES) ||
                stack.isIn(ItemTags.SHOVELS);
    }
}
