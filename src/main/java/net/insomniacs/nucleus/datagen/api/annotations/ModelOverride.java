package net.insomniacs.nucleus.datagen.api.annotations;

import net.minecraft.data.client.Models;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModelOverride {
    String model() default "HANDHELD";
    // This is used within the common namespace and client, so I went with the: reflective option.
    Class<?> modelHome() default Models.class;
    //
}
