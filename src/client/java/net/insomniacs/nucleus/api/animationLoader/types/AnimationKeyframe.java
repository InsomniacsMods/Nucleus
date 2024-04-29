package net.insomniacs.nucleus.api.animationLoader.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.api.animationLoader.util.InterpolationType;
import net.insomniacs.nucleus.api.utils.CodecUtils;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public record AnimationKeyframe (
//		Vector3f pre,
//		Vector3f post,
		Vec3d pre,
		Vec3d post,
		InterpolationType interpolation
) {

	public static final Codec<AnimationKeyframe> ADVANCED_CODEC = RecordCodecBuilder.create(instance -> instance.group(
//			VectorCodec.CODEC.optionalFieldOf("pre", VectorCodec.ZERO).forGetter(AnimationKeyframe::pre),
//			VectorCodec.CODEC.optionalFieldOf("post", VectorCodec.ZERO).forGetter(AnimationKeyframe::pre),
//			InterpolationType.CODEC.optionalFieldOf("lerp_mode", InterpolationType.LINEAR).forGetter(AnimationKeyframe::interpolation)
			Vec3d.CODEC.optionalFieldOf("pre", Vec3d.ZERO).forGetter(null),
			Vec3d.CODEC.optionalFieldOf("post", Vec3d.ZERO).forGetter(null),
			InterpolationType.CODEC.optionalFieldOf("lerp_mode", InterpolationType.LINEAR).forGetter(null)
	).apply(instance, AnimationKeyframe::new));

	public static final Codec<AnimationKeyframe> SIMPLE_CODEC = Vec3d.CODEC.flatComapMap(AnimationKeyframe::simple, null);

	public static final Codec<AnimationKeyframe> CODEC = CodecUtils.merge(ADVANCED_CODEC, SIMPLE_CODEC);

	public static AnimationKeyframe simple(Vec3d vector) {
		// TODO if animation is too smooth, set to InterpolationType.CUBIC
		return new AnimationKeyframe(Vec3d.ZERO, vector, InterpolationType.LINEAR);
	}

	public Keyframe toKeyframe(float time) {
		return new Keyframe(time, getRotation(post), interpolation.getType());
	}

	private Vector3f getRotation(Vec3d vec) {
		return AnimationHelper.createRotationalVector((float)vec.x, (float)vec.y, (float)vec.z);
	}

//	private Vector3f getRotation(Vec3d vec) {
//		return AnimationHelper.createRotationalVector(vec.x, vec.y, vec.z);
//	}

}
