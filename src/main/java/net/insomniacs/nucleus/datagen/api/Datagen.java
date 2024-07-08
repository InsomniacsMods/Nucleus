package net.insomniacs.nucleus.datagen.api;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.insomniacs.nucleus.datagen.impl.NucleusLanguageProvider;
import net.insomniacs.nucleus.datagen.impl.NucleusLootTableProvider;
import net.insomniacs.nucleus.datagen.impl.NucleusModelProvider;
import net.insomniacs.nucleus.datagen.impl.NucleusRecipeProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry.Reference;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Datagen {

    public static void generateData(final String modID, final FabricDataGenerator generator, Class<?>... registries) {
        Predicate<Reference<?>> filteredID = (reference) ->
                Objects.equals(reference.getIdAsString().split(":")[0], modID);

        generateData(filteredID, generator, registries);
    }

    public static void generateData(final Predicate<Reference<?>> registryFilter, final FabricDataGenerator generator, Class<?>... registries) {
        Objects.requireNonNull(registryFilter, "modID cannot be null.");
        Objects.requireNonNull(generator, "generator cannot be null, silly goose.");

        // Standard variables
        // Now we simply make our streams -> see PR 'Datagen improvement' for explanation;
        var pack = generator.createPack();
        var refMap = mergedMap(registryFilter, registries);

        pack.addProvider((a,b) -> new NucleusLanguageProvider(a,b, refMap));
        pack.addProvider((a,b) -> new NucleusLootTableProvider(a,b, refMap));
        pack.addProvider((a,b) -> new NucleusRecipeProvider(a,b, refMap));
        pack.addProvider((a,b) -> new NucleusModelProvider(a, refMap));
    }

    static Map<Registry<?>, List<Reference<?>>> getFilteredMap(Predicate<Reference<?>> filter) {
        return Registries.REGISTRIES
                .streamEntries()
                .map(reference -> (Registry<?>) reference.value())
                .collect(Collectors.toUnmodifiableMap(
                        ref -> ref,
                        ref -> ref.streamEntries()
                                .filter(filter)
                                .collect(Collectors.toUnmodifiableList())
                ));
    }

    static Object getAccessibleField(Field field) {
        field.setAccessible(true);
        try {
            return field.get(null);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    static Map<Object, Annotation[]> getFieldAnnotations(Class<?>[] registries) {
        return Arrays.stream(registries)
            .map(Class::getFields)
            .flatMap(Arrays::stream)
            .collect(Collectors.toUnmodifiableMap(
                   Datagen::getAccessibleField,
                   Field::getDeclaredAnnotations
            )
        );
    }

    static Map<Registry<?>, RefAnnotationPair[]> mergedMap(Predicate<Reference<?>> filter, Class<?>[] registries) {
        var registryMap = getFilteredMap(filter);
        var fieldAnnotation = getFieldAnnotations(registries);

        return registryMap.entrySet().stream().collect(Collectors.toUnmodifiableMap(
            Map.Entry::getKey,
            entry -> entry.getValue()
                .stream()
                .map(reference -> new RefAnnotationPair(
                        reference,
                        fieldAnnotation.get(reference.value())))
                .toArray(RefAnnotationPair[]::new)
        ));
    }

    public record RefAnnotationPair(Reference<?> reference, Annotation[] annotations) {
        @Override
        public String toString() {
            return "RefAnnotationPair{" +
                    "reference=" + reference +
                    ", annotations=" + Arrays.toString(annotations) +
                    '}';
        }
    }
}
