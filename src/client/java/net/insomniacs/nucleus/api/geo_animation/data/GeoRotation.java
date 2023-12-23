package net.insomniacs.nucleus.api.geo_animation.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.utils.Vec3f;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public record GeoRotation(
    Vec3f rotation,
    String interpolation
) {

    public static final Codec<GeoRotation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3f.CODEC.fieldOf("post").forGetter(null),
            Codec.STRING.fieldOf("lerp_mode").forGetter(null)
    ).apply(instance, GeoRotation::new));

    public Keyframe toKeyframe(float timestamp) {
        return new Keyframe(
                timestamp,
                AnimationHelper.createRotationalVector(rotation.x, rotation.y, rotation.z),
                Transformation.Interpolations.LINEAR
        );
    }

}
