package net.insomniacs.nucleus.api.animationLoader.types;

import com.mojang.serialization.Codec;
import net.minecraft.client.render.entity.animation.Keyframe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record AnimationTimeline(
		Map<Float, AnimationKeyframe> timeline
) {

	public static final Codec<AnimationTimeline> CODEC = Codec.unboundedMap(
			Codec.STRING, AnimationKeyframe.CODEC
	).flatComapMap(AnimationTimeline::simple, null);

	public static AnimationTimeline simple(Map<String, AnimationKeyframe> timeline) {
		Map<Float, AnimationKeyframe> result = new HashMap<>();
		timeline.forEach(
				(time, animation) -> result.put(Float.valueOf(time), animation)
		);
		return new AnimationTimeline(result);
	}

	public Keyframe[] getKeyframes() {
		List<Keyframe> result = new ArrayList<>();
		timeline.forEach(
				(time, animation) -> result.add(animation.toKeyframe(time))
		);
		return result.toArray(Keyframe[]::new);
	}

}
