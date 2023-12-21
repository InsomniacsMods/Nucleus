package net.insomniacs.nucleus.api.geo_anim.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.utils.Vec3i;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public record BoneRotation (
    Vec3i rotation,
    String interpolation
) {

    public static final Codec<BoneRotation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3i.CODEC.fieldOf("post").forGetter(null),
            Codec.STRING.fieldOf("lerp_mode").forGetter(null)
    ).apply(instance, BoneRotation::new));

    public Keyframe toKeyframe(float timestamp) {
        return new Keyframe(
                timestamp,
                AnimationHelper.createRotationalVector(15f, 0f, 0f),
                Transformation.Interpolations.LINEAR
        );
    }

}
