package net.insomniacs.nucleus.api.animationLoader.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.api.animationLoader.util.InterpolationType;
import net.insomniacs.nucleus.api.utils.CodecUtils;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.util.math.Vec3d;

public record AnimationKeyframe (
		Vec3d pre,
		Vec3d post,
		InterpolationType interpolation
) {

	public static final Codec<AnimationKeyframe> ADVANCED_CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Vec3d.CODEC.optionalFieldOf("pre", Vec3d.ZERO).forGetter(AnimationKeyframe::pre),
			Vec3d.CODEC.optionalFieldOf("post", Vec3d.ZERO).forGetter(AnimationKeyframe::pre),
			InterpolationType.CODEC.optionalFieldOf("lerp_mode", InterpolationType.LINEAR).forGetter(AnimationKeyframe::interpolation)
	).apply(instance, AnimationKeyframe::new));

	public static final Codec<AnimationKeyframe> SIMPLE_CODEC = Vec3d.CODEC.xmap(AnimationKeyframe::simple, AnimationKeyframe::post);

	public static final Codec<AnimationKeyframe> CODEC = CodecUtils.combine(ADVANCED_CODEC, SIMPLE_CODEC);

	public static AnimationKeyframe simple(Vec3d vector) {
		// TODO if animation is too smooth, replace default for cubic
		return new AnimationKeyframe(Vec3d.ZERO, vector, InterpolationType.LINEAR);
	}

	public Keyframe toKeyframe(float time) {
		return new Keyframe(
				time,
				AnimationHelper.createRotationalVector(0f, 0f, 0f),
				interpolation.getType()
		);
	}

}
