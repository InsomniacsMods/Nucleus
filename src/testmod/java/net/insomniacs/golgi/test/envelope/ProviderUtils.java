package net.insomniacs.golgi.test.envelope;

import net.insomniacs.nucleus.datagen.api.NucleusDataGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProviderUtils {

    public static String parseId(Identifier id) {
        return Arrays
                .stream(id.getPath().split("([_.])")) // match either _ or .
                .map(ProviderUtils::toUppercase)
                .collect(Collectors.joining(" "));
    }

    private static String toUppercase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static Stream<RegistryEntry.Reference<?>> streamAllRegistries() {
        Stream<RegistryEntry.Reference<?>> result = Stream.of();
        for (Identifier id : Registries.REGISTRIES.getIds()) {
            var registry = Registries.REGISTRIES.get(id);
            Objects.requireNonNull(registry);
            result = Stream.concat(result, streamRegistry(registry));
        }
        return result;
    }

    public static <T> Stream<RegistryEntry.Reference<T>> streamRegistry(
            Registry<T> registry
    ) {
        return registry.streamEntries()
                .filter(EnvelopeAPI::isModRegistered);
    }

}
