package net.insomniacs.nucleus.api.animationLoader.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.api.animationLoader.util.InterpolationType;
import net.insomniacs.nucleus.api.animationLoader.util.VectorCodec;
import net.insomniacs.nucleus.api.utils.CodecUtils;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public record AnimationKeyframe (
		Vector3f pre,
		Vector3f post,
		InterpolationType interpolation
) {

	public static final Codec<AnimationKeyframe> ADVANCED_CODEC = RecordCodecBuilder.create(instance -> instance.group(
			VectorCodec.CODEC.optionalFieldOf("pre", VectorCodec.ZERO).forGetter(AnimationKeyframe::pre),
			VectorCodec.CODEC.optionalFieldOf("post", VectorCodec.ZERO).forGetter(AnimationKeyframe::pre),
			InterpolationType.CODEC.optionalFieldOf("lerp_mode", InterpolationType.LINEAR).forGetter(AnimationKeyframe::interpolation)
	).apply(instance, AnimationKeyframe::new));

	public static final Codec<AnimationKeyframe> SIMPLE_CODEC = VectorCodec.CODEC.xmap(AnimationKeyframe::simple, AnimationKeyframe::post);

	public static final Codec<AnimationKeyframe> CODEC = CodecUtils.combine(ADVANCED_CODEC, SIMPLE_CODEC);

	public static AnimationKeyframe simple(Vector3f vector) {
		// TODO if animation is too smooth, replace default for cubic
		return new AnimationKeyframe(VectorCodec.ZERO, vector, InterpolationType.LINEAR);
	}

	public Keyframe toKeyframe(float time) {
		return new Keyframe(
				time,
				AnimationHelper.createRotationalVector(post.x, post.y, post.z),
				interpolation.getType()
		);
	}

}
