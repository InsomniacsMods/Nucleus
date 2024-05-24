package net.insomniacs.nucleus.datagen.api.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Translate {
	String name() default "";
}
