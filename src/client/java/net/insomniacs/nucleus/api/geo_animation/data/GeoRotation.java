package net.insomniacs.nucleus.api.geo_animation.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.util.math.Vec3d;

public record GeoRotation(
    Vec3d rotation,
    String interpolation
) {

    public static final Codec<GeoRotation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3d.CODEC.fieldOf("post").forGetter(null),
            Codec.STRING.fieldOf("lerp_mode").forGetter(null)
    ).apply(instance, GeoRotation::new));

    public Keyframe toKeyframe(float timestamp) {
        return new Keyframe(
                timestamp,
                AnimationHelper.createRotationalVector(
                        (float)rotation.x, (float)rotation.y, (float)rotation.z
                ),
                Transformation.Interpolations.LINEAR
        );
    }

}
