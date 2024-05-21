package net.insomniacs.nucleus.impl.utility;

import net.minecraft.util.Identifier;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class IDMapper {

    private IDMapper() {}

    public static Map<Identifier, List<Annotation>> mapAnnotations(List<Class<?>> classes) {
        classes.stream()
                .flatMap(clazz -> Arrays.stream(clazz.getFields()))
                .forEach(field -> {
                    field.setAccessible(true);

                    try {
                        // Get value of object
                        var fieldValue = field.get(null);

                        // Refer to the registries

                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }


        });
        return null;
    }

    public static Object ScrapeRegistries() {
        return null;
    }

}
