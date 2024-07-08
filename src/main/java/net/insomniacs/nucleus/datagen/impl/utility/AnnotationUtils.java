package net.insomniacs.nucleus.datagen.impl.utility;

import net.insomniacs.nucleus.datagen.api.annotations.DatagenExempt;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * A class that exists to provide a more functional way to use Annotations.
 */
public final class AnnotationUtils {

    private AnnotationUtils() {}

    // This is ugly and i hate it.
    @SuppressWarnings("unchecked")
    public static <T extends Annotation> Optional<T> getAnnotation(Annotation[] annotations, Class<T> annotation) {
        if (annotations == null)
            return Optional.empty();
        for (Annotation heldAnnotation : annotations) {
            if (heldAnnotation.getClass().equals(annotation))
                return Optional.of((T) heldAnnotation);
        }
        return Optional.empty();
    }

    public static <T extends Annotation> Optional<T> getAnnotation(Class<?> entry, Class<T> annotation) {
        return Optional.ofNullable(entry.getAnnotation(annotation));
    }

    public static boolean isExempt(Class<?> clazz, DatagenExempt.Exemption exemption) {
        var annotation = getAnnotation(clazz, DatagenExempt.class);
        if (annotation.isPresent()) {
            var exempt = annotation.get();

            for (var entry : exempt.value()) if (entry == exemption || entry == DatagenExempt.Exemption.ALL) {
                return true;
            }
        }
        return false;
    }

}
