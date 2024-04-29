package net.insomniacs.nucleus.api.animationLoader.types;

import com.mojang.serialization.Codec;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Transformation;

import java.util.Map;

public record AnimationBones(
		Map<String, AnimationTransformation> animations
) {

	public static final Codec<AnimationBones> CODEC = Codec.unboundedMap(
			Codec.STRING, AnimationTransformation.CODEC
	).flatComapMap(AnimationBones::new, null);

	public Animation.Builder addAnimations(Animation.Builder builder) {
		for (var entry : animations.entrySet()) {
			String partName = entry.getKey();
			AnimationTransformation animation = entry.getValue();
			for (Transformation transformation : animation.getTransformations()) {
				builder = builder.addBoneAnimation(partName, transformation);
			}
		}
		return builder;
	}

}
