package net.insomniacs.nucleus.api.animationLoader.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Transformation;

import java.util.Map;

public record AnimationData (
		float length,
		boolean loop,
		Map<String, AnimationTransformation> animations
) {

	public static final UnboundedMapCodec<String, AnimationTransformation> MAP_CODEC = Codec.unboundedMap(
			Codec.STRING,
			AnimationTransformation.CODEC
	);

	public static final Codec<AnimationData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.FLOAT.fieldOf("animation_length").forGetter(AnimationData::length),
			Codec.BOOL.optionalFieldOf("loop", false).forGetter(AnimationData::loop),
			MAP_CODEC.fieldOf("bones").forGetter(AnimationData::animations)
	).apply(instance, AnimationData::new));

	public Animation toAnimation() {
		Animation.Builder result = Animation.Builder.create(length);
		if (loop) result = result.looping();

		for (var entry : animations.entrySet()) {
			String partName = entry.getKey();
			AnimationTransformation animation = entry.getValue();
			for (Transformation transformation : animation.getTransformations()) {
				result = result.addBoneAnimation(partName, transformation);
			}
		}

		return result.build();
	}

}
