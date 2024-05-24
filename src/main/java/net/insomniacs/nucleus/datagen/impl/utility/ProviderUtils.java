package net.insomniacs.nucleus.datagen.impl.utility;

import net.insomniacs.nucleus.datagen.api.NucleusDataGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class ProviderUtils {

    public static <T> String parseID(Identifier id) {
        return Arrays
                .stream(id.getPath().split("_"))
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

}
