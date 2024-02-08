package net.insomniacs.nucleus.api.geo_animation;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.api.geo_animation.data.GeoAnimationHandler;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

import java.util.Map;

public class GeoAnimation {

    public final float length;
    public final LoopMode loopMode;
    public final Map<String, GeoAnimationHandler> animations;

    public GeoAnimation (
            float length,
            Either<String, Boolean> loop,
            Map<String, GeoAnimationHandler> animations
    ) {
        this.length = length;
        this.animations = animations;

        LoopMode tempLoopMode;
        tempLoopMode = LoopMode.PLAY_ONCE;
        if (loop.left().isPresent()) {
            if (loop.left().get().equals("hold_on_last_frame")) tempLoopMode = LoopMode.HOLD_LAST;
        } else if (loop.right().isPresent()) {
            if (loop.right().get()) tempLoopMode = LoopMode.LOOP;
        }
        this.loopMode = tempLoopMode;
    }

    public static final Codec<GeoAnimation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("animation_length").forGetter(null),
            Codec.either(Codec.STRING, Codec.BOOL).optionalFieldOf("loop", Either.right(false)).forGetter(null),
            Codec.unboundedMap(Codec.STRING, GeoAnimationHandler.CODEC).fieldOf("bones").forGetter(null)
    ).apply(instance, GeoAnimation::new));

    public Animation toAnimation() {
        Animation.Builder builder = Animation.Builder.create(this.length).looping();
        this.animations.forEach((name, animation) -> {
            Keyframe[] keyframes = animation.toKeyframes().toArray(Keyframe[]::new);
            builder.addBoneAnimation(name, new Transformation(Transformation.Targets.ROTATE, keyframes));
        });
        return builder.build();
    }

    public enum LoopMode {
        PLAY_ONCE,
        HOLD_LAST,
        LOOP
    }

}
