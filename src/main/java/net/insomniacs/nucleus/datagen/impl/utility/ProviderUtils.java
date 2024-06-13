package net.insomniacs.nucleus.datagen.impl.utility;

import net.insomniacs.nucleus.datagen.api.NucleusDataGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ProviderUtils {

    public static <T> String parseID(Identifier id) {
        return Arrays
                .stream(id.getPath().split("([_.])")) // match either _ or .
                .map(ProviderUtils::makeUpperCase)
                .collect(Collectors.joining(" "));
    }

    private static String makeUpperCase(String str) {
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }

    public static void streamAllRegistries(NucleusDataGenerator dataGenerator, BiConsumer<Registry<?>, RegistryEntry.Reference<?>> entryConsumer) {
        Registries.REGISTRIES
                .getIds()
                .forEach(identifier -> {
                    var registry = Registries.REGISTRIES.get(identifier);
                    Objects.requireNonNull(registry);

                    registry.streamEntries()
                            .filter(dataGenerator::filterMod)
                            .forEach(entryRef -> entryConsumer.accept(registry, entryRef));
                });
    }

    public static <T> void streamRegistry(
            Registry<T> registry,
            NucleusDataGenerator dataGenerator,
            Consumer<RegistryEntry.Reference<T>> entryConsumer
    ) {
        registry.streamEntries()
                .filter(dataGenerator::filterMod)
                .forEach(entryConsumer);
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<? extends Registry<T>> getRegistry(T clazzType) {
        return Registries.REGISTRIES
            .streamEntries().map(RegistryEntry.Reference::value)
            .filter(registry -> {
                var inputClazz = clazzType.getClass();
                var parameterClazz = (Class<?>)
                        ((ParameterizedType) registry.getClass().getGenericSuperclass())
                                .getActualTypeArguments()[0];
                return parameterClazz.equals(inputClazz) || parameterClazz.isAssignableFrom(inputClazz);
        }).map(registryCapture -> (Registry<T>) registryCapture).findFirst(); // Give the compiler a middle finger. I know what this should be and i am RIGHT.
    }
}
