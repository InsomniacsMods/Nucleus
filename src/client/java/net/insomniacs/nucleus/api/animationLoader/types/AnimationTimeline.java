package net.insomniacs.nucleus.api.animationLoader.types;

import com.mojang.serialization.Codec;
import net.minecraft.client.render.entity.animation.Keyframe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationTimeline {

	public static final Codec<AnimationTimeline> CODEC = Codec.unboundedMap(
			Codec.STRING, AnimationKeyframe.CODEC
	).flatComapMap(AnimationTimeline::new, null);


	private final Map<Float, AnimationKeyframe> timeline;

	public AnimationTimeline(Map<String, AnimationKeyframe> timeline) {
		this.timeline = new HashMap<>();
		timeline.forEach(
				(time, animation) -> this.timeline.put(Float.valueOf(time), animation)
		);
	}


	public Keyframe[] getKeyframes() {
		List<Keyframe> result = new ArrayList<>();
		timeline.forEach(
				(time, animation) -> result.add(animation.toKeyframe(time))
		);
		return result.toArray(Keyframe[]::new);
	}

}
