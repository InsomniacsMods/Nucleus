package net.insomniacs.nucleus.datagen.api.annotations;

import net.minecraft.data.client.Models;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ItemModel {
    String model() default "HANDHELD";
    Class<?> modelHome() default Models.class;
    boolean cancel() default false;
}
