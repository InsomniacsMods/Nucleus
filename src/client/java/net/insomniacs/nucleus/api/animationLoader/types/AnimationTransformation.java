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
		List<Transformation> result = new ArrayList<>();
		transformations.forEach((transformation, timeline) -> {
			Keyframe[] frames = timeline.getKeyframes();
			Transformation value = new Transformation(transformation.getType(), frames);
			result.add(value);
		});
		return result;
	}

}
