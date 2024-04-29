package net.insomniacs.nucleus.api.animationLoader.types;

import com.mojang.serialization.Codec;
import net.insomniacs.nucleus.api.animationLoader.util.TransformationType;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record AnimationTransformation(
		Map<TransformationType, AnimationTimeline> transformations
) {

	public static final Codec<AnimationTransformation> CODEC = Codec.unboundedMap(
			TransformationType.CODEC, AnimationTimeline.CODEC
	).flatComapMap(AnimationTransformation::new, null);

	public List<Transformation> getTransformations() {
		return transformations.entrySet()
				.stream()
				.map(this::fromEntry)
				.toList();
	}

	private Transformation fromEntry(Map.Entry<TransformationType, AnimationTimeline> entry) {
		return fromData(entry.getKey(), entry.getValue());
	}

	private Transformation fromData(TransformationType transformation, AnimationTimeline timeline) {
		Keyframe[] frames = timeline.getKeyframes();
		return new Transformation(transformation.getType(), frames);
	}

}
