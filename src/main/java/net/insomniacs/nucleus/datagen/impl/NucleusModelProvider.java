package net.insomniacs.nucleus.datagen.impl;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.insomniacs.nucleus.api.utils.TypeContainer;
import net.insomniacs.nucleus.datagen.api.NucleusDataGenerator;
import net.insomniacs.nucleus.datagen.api.annotations.DatagenExempt;
import net.insomniacs.nucleus.datagen.api.annotations.ModelOverride;
import net.insomniacs.nucleus.datagen.impl.utility.AnnotationUtils;
import net.insomniacs.nucleus.datagen.impl.utility.ProviderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static net.insomniacs.nucleus.api.utils.TypeContainer.Multi;
import static net.insomniacs.nucleus.api.utils.TypeContainer.Single;
import static net.insomniacs.nucleus.datagen.impl.utility.AnnotationUtils.getAnnotation;


//@ItemModel(model = "CUBE")
public class NucleusModelProvider extends FabricModelProvider {

    // Generic behaviors for inheritance based assignment.
    private static final Map<TypeContainer<Class<? extends Block>>, BlockStateGenerator> blockStateMap = Map.ofEntries(
            Map.entry(new Single<>(CampfireBlock.class), (modelGenerator, block, id) ->
                    modelGenerator.registerCampfire(block)),
            Map.entry(new Single<>(PressurePlateBlock.class), (modelGenerator, block, id) ->
                    modelGenerator.registerWeightedPressurePlate(block, block)),
            Map.entry(new Single<>(Block.class), (modelGenerator, block, id) ->
                    modelGenerator.registerSimpleCubeAll(block))
    );

    private static final Map<TypeContainer<Class<?>>, Model> classModelMap = Map.ofEntries(
            Map.entry(new Multi<>(
                    SwordItem.class,
                    PickaxeItem.class,
                    AxeItem.class,
                    HoeItem.class
            ), Models.HANDHELD),

            Map.entry(new Single<>(FishingRodItem.class), Models.HANDHELD_ROD),
            Map.entry(new Single<>(MaceItem.class), Models.HANDHELD_MACE),
            Map.entry(new Single<>(BlockItem.class), Models.GENERATED)
    );

    private final NucleusDataGenerator generator;

    public NucleusModelProvider(FabricDataOutput output, NucleusDataGenerator generator) {
        super(output);
        this.generator = generator;
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        ProviderUtils.streamRegistry(Registries.BLOCK, generator, block -> {
            var value = block.value();
            var blockClazz = value.getClass();
            var id = Registries.BLOCK.getId(value);

            if (isExempt(blockClazz)) return;

//            var state = blockStateMap.get(blockClazz);
//            if (state == null) return;
//            state.generateModel(blockStateModelGenerator, value, id);

            blockStateModelGenerator.registerSimpleCubeAll(value);
        });
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        ProviderUtils.streamRegistry(Registries.ITEM, generator, item -> {
            var value = item.value(); // We know it at minimum extends item.
            var itemClazz = value.getClass();

            var modelAnnotation = getAnnotation(item, ModelOverride.class);

            if (isExempt(itemClazz)) return;

            // Optional chaining jumpscare
            var model = getModelFromAnnotation(modelAnnotation)
                    .orElse(getModelFromInheritance(itemClazz)
                            .orElse(Models.GENERATED));

            itemModelGenerator.register(value, model);
        });
    }

    /**
     * Get model from annotation data.
     * @param modelAnnotation model annotation of field
     */
    Optional<Model> getModelFromAnnotation(ModelOverride modelAnnotation) {
        Optional<Model> model = Optional.empty();
        if (modelAnnotation != null) try {
            var modelsClazz = modelAnnotation.modelHome();
            var modelField = modelsClazz.getField(modelAnnotation.model().toUpperCase());

            modelField.setAccessible(true);
            model = Optional.ofNullable((Model) modelField.get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.info("Model: " + modelAnnotation.model() + " doesn't exist within " + modelAnnotation.modelHome() + ".");
        }
        return model;
    }

    /**
     * Returns a model based on an inheritance map.
     * @param clazz class of object to get model from
     * @return the changed model
     */
    Optional<Model> getModelFromInheritance(Class<?> clazz) {
        return classModelMap.keySet()
                .stream()
                .filter(typeContainer -> switch (typeContainer) {
                    case Single<Class<?>> single ->
                            single.element().isAssignableFrom(clazz);
                    case Multi<Class<?>> multi ->
                            Arrays.stream(multi.elements()).anyMatch(element -> element.isAssignableFrom(clazz));
                })
                .map(classModelMap::get).findFirst();
    }

    boolean isExempt(Class<?> clazz) {
        return AnnotationUtils.isExempt(clazz, DatagenExempt.Exemption.MODEL);
    }

    @FunctionalInterface
    public interface BlockStateGenerator {
        void generateModel(BlockStateModelGenerator modelGenerator, Block block, Identifier id);
    }
}
