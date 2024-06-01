package net.insomniacs.nucleus.datagen.impl.utility;

import net.insomniacs.nucleus.datagen.api.annotations.DatagenExempt;

import java.lang.annotation.Annotation;

/**
 * A class that exists to provide a more functional way to use Annotations.
 */
public final class AnnotationUtils {

    private AnnotationUtils() {}

    public static <T extends Annotation> T getAnnotation(Class<?> entry, Class<T> annotation) {
        return entry.getAnnotation(annotation);
    }

    public static boolean isExempt(Class<?> clazz, DatagenExempt.Exemption exemption) {
        var annotation = getAnnotation(clazz, DatagenExempt.class);
        if (annotation == null) return false;

        for (var entry : annotation.value()) if (entry == exemption || entry == DatagenExempt.Exemption.ALL) {
            return true;
        }
        return false;
    }

}
