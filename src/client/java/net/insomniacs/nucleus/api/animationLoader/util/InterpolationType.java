package net.insomniacs.nucleus.api.animationLoader.util;

import com.mojang.serialization.Codec;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.util.StringIdentifiable;

public enum InterpolationType implements StringIdentifiable {

	LINEAR("linear", Transformation.Interpolations.LINEAR),
	CUBIC("cubic", Transformation.Interpolations.CUBIC);

	public static final Codec<InterpolationType> CODEC = StringIdentifiable.createCodec(InterpolationType::values);

	private final String name;
	private final Transformation.Interpolation interpolation;

	InterpolationType(String name, Transformation.Interpolation interpolation) {
		this.name = name;
		this.interpolation = interpolation;
	}

	@Override
	public String asString() {
		return name;
	}

	public Transformation.Interpolation getType() {
		return interpolation;
	}

}
