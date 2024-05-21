package net.insomniacs.nucleus.impl.utility;

import net.insomniacs.nucleus.api.annotations.Translate;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * A class that exists to provide a more functional way to use Annotations.
 */
public final class AnnotationUtils {

    private AnnotationUtils() {}

    public static <T extends Annotation> T getAnnotation(Class<?> entry, Class<T> annotation) {
        return entry.getAnnotation(annotation);
    }

}
