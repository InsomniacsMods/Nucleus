package net.insomniacs.nucleus.datagen.api.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SuppressWarnings("unused")
@Retention(RetentionPolicy.RUNTIME)
public @interface DatagenExempt {

    DatagenExempt.Exemption[] value() default {DatagenExempt.Exemption.ALL};

    enum Exemption {
        ALL,
        MODEL,
        TRANSLATE,
        LOOT_TABLE,
        RECIPES
    }

}
