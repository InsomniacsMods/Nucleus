package net.insomniacs.nucleus.datagen.api.annotations;

@SuppressWarnings("unused")
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
