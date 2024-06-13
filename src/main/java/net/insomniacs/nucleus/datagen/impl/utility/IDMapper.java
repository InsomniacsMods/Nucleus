package net.insomniacs.nucleus.datagen.impl.utility;

import net.minecraft.util.Identifier;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.insomniacs.nucleus.datagen.impl.utility.ProviderUtils.getRegistry;

public final class IDMapper {

    private IDMapper() {}

    public static Map<Identifier, List<Annotation>> mapAnnotations(List<Class<?>> classes) {
        var entryMap = new HashMap<Identifier, List<Annotation>>();

        classes.stream()
            .flatMap(clazz -> Arrays.stream(clazz.getFields()))
            .forEach(field -> {
                field.setAccessible(true);

                try {
                    // Get value of object, since registries return instances of Block and Item or Biome or EntityType we can use these as keys.
                    var fieldValue = field.get(null);
                    var registry = getRegistry(fieldValue);
                    var fieldAnnotations = field.getDeclaredAnnotations();

                    registry.ifPresent(registryValue -> {
                        var id = registryValue.getId(fieldValue);
                        entryMap.put(id, Arrays.stream(fieldAnnotations).toList());
                    });

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        );
        return entryMap;
    }


}
