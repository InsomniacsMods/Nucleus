package net.insomniacs.nucleus.api.annotations;

import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;

import static net.minecraft.data.client.Models.*;

public @interface ModelOverride {
    String model() default "HANDHELD";
    Class<?> modelHome() default Models.class;
}
