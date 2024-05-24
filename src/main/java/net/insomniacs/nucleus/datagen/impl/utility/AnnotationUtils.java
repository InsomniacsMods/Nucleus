package net.insomniacs.nucleus.datagen.impl.utility;

import java.lang.annotation.Annotation;

/**
 * A class that exists to provide a more functional way to use Annotations.
 */
public final class AnnotationUtils {

    private AnnotationUtils() {}

    public static <T extends Annotation> T getAnnotation(Class<?> entry, Class<T> annotation) {
        return entry.getAnnotation(annotation);
    }

}
