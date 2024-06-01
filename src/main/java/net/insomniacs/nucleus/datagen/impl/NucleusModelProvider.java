package net.insomniacs.nucleus.datagen.impl;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.insomniacs.nucleus.datagen.api.NucleusDataGenerator;
import net.insomniacs.nucleus.datagen.api.annotations.DatagenExempt;
import net.insomniacs.nucleus.datagen.api.annotations.ModelOverride;
import net.insomniacs.nucleus.datagen.impl.utility.AnnotationUtils;
import net.insomniacs.nucleus.datagen.impl.utility.ProviderUtils;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static net.insomniacs.nucleus.datagen.impl.utility.AnnotationUtils.getAnnotation;

//@ItemModel(model = "CUBE")
public class NucleusModelProvider extends FabricModelProvider {

    // TODO: i shall eventually transition this to something else, maybe, don't quote me on that.
    //  Probably something to group models easier? Most likely.
    //   FUCK YOU PAST ME I DID IT BEFORE IT CAME TIME TO COMMIT
    private static final Map<TypeContainer<Class<?>>, Model> classModelMap = Map.ofEntries(
            Map.entry(new TypeContainer.Multi<>(
                    SwordItem.class,
                    PickaxeItem.class,
                    AxeItem.class,
                    HoeItem.class
            ), Models.HANDHELD),

            Map.entry(new TypeContainer.Single<>(FishingRodItem.class), Models.HANDHELD_ROD),
            Map.entry(new TypeContainer.Single<>(MaceItem.class), Models.HANDHELD_MACE),
            Map.entry(new TypeContainer.Single<>(BlockItem.class), Models.GENERATED)
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

            if (isExempt(blockClazz)) return;

            blockStateModelGenerator.registerSimpleCubeAll(value);
        });
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        ProviderUtils.streamRegistry(Registries.ITEM, generator, item -> {
            var value = item.value(); // We know it at minimum extends item.
            var itemClazz = value.getClass();

            var modelAnnotation = getAnnotation(itemClazz, ModelOverride.class);

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
                    case TypeContainer.Single<Class<?>> single ->
                            single.element.isAssignableFrom(clazz);
                    case TypeContainer.Multi<Class<?>> multi ->
                            Arrays.stream(multi.elements).anyMatch(element -> element.isAssignableFrom(clazz));
                })
                .map(classModelMap::get).findFirst();
    }

    // I *WISH* i could put methods in annotations. That way annotations could just hold this function for me.
    boolean isExempt(Class<?> clazz) {
        var exemptionAnnotation = getAnnotation(clazz, DatagenExempt.class);
        return AnnotationUtils.isExempt(exemptionAnnotation, DatagenExempt.Exemption.MODEL);
    }

    // FUCK IT WE BALL, IM MAKING THE MULTI-FOLD THING.
    @SuppressWarnings("unused")
    sealed interface TypeContainer<T> {
        record Single<T>(T element) implements TypeContainer<T> { }
        record Multi<T>(T... elements) implements TypeContainer<T> {
            @SafeVarargs
            public Multi {
            }
        }
    }
}
