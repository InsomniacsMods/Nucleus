package net.insomniacs.nucleus.api.geo_anim.data.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.utils.Vec3f;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public record BoneScale (
        Vec3f scale
) {

    public static final Codec<BoneScale> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3f.CODEC.fieldOf("scale").forGetter(null)
    ).apply(instance, BoneScale::new));

    public Keyframe toKeyframe(float timestamp) {
        return new Keyframe(
                timestamp,
                AnimationHelper.createScalingVector(scale.x, scale.y, scale.z),
                Transformation.Interpolations.LINEAR
        );
    }

}
