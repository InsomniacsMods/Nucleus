package net.insomniacs.nucleus.api.animationLoader.util;

import com.mojang.serialization.Codec;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.util.StringIdentifiable;

public enum TransformationType implements StringIdentifiable {

	TRANSLATE("position", Transformation.Targets.TRANSLATE),
	ROTATE("rotation", Transformation.Targets.ROTATE),
	SCALE("scale", Transformation.Targets.SCALE);

	public static final Codec<TransformationType> CODEC = StringIdentifiable.createCodec(TransformationType::values);

	private final String name;
	private final Transformation.Target target;

	TransformationType(
			String name,
			Transformation.Target target
	) {
		this.name = name;
		this.target = target;
	}

	@Override
	public String asString() {
		return name;
	}

	public Transformation.Target getType() {
		return target;
	}
}
