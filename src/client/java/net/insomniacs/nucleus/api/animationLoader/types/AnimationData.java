package net.insomniacs.nucleus.api.animationLoader.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.api.animationLoader.util.LoopMode;
import net.minecraft.client.render.entity.animation.Animation;

public record AnimationData (
		float length,
		LoopMode loop,
		AnimationBones bones
) {

	public static final Codec<AnimationData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.FLOAT.fieldOf("animation_length").forGetter(null),
			LoopMode.CODEC.optionalFieldOf("loop", LoopMode.ONCE).forGetter(null),
			AnimationBones.CODEC.fieldOf("bones").forGetter(null)
	).apply(instance, AnimationData::new));

	public Animation toAnimation() {
		Animation.Builder builder = Animation.Builder.create(length);
		if (loop == LoopMode.LOOP) builder = builder.looping();
		builder = bones.addAnimations(builder);
		return builder.build();
	}

}
