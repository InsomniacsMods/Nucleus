package net.insomniacs.nucleus.api.geo_anim.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

import java.util.Map;

public record GeoAnimation(
        float animationLength,
        Map<String, BoneAnimation> boneAnimations
) {

    public static final Codec<GeoAnimation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("animation_length").forGetter(null),
            Codec.unboundedMap(Codec.STRING, BoneAnimation.CODEC).fieldOf("bones").forGetter(null)
    ).apply(instance, GeoAnimation::new));

    public Animation toAnimation() {
        Animation.Builder builder = Animation.Builder.create(this.animationLength).looping();
        this.boneAnimations.forEach((name, animation) -> {
            Keyframe[] keyframes = animation.toKeyframes().toArray(Keyframe[]::new);
            builder.addBoneAnimation("Torso", new Transformation(Transformation.Targets.ROTATE, keyframes));
        });
        return builder.build();
    }

}
