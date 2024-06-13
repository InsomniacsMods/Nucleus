package net.insomniacs.nucleus.datagen.impl.utility;

import net.insomniacs.nucleus.datagen.api.NucleusDataGenerator;
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

    @SuppressWarnings("unchecked")
    public static <T extends Annotation, E> T getAnnotation(E entry, Class<T> annotation) {
        var annotationEntry = getAnnotation(entry.getClass(), annotation);
        if (ProviderUtils.getRegistry(entry).isPresent()) {
            var value = ProviderUtils.getRegistry(entry).get();
            var entryID = value.getId(entry);
            var annotationList = NucleusDataGenerator.ANNOTATION_MAP.get(entryID);
            for (var annotationIn : annotationList) if (annotationIn.annotationType().equals(annotation)) {
                annotationEntry = (T) annotationIn; // Bitch i know the type, stfu.
            }
        }
        return annotationEntry;
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
