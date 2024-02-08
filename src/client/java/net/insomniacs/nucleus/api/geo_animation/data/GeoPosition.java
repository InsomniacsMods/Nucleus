package net.insomniacs.nucleus.api.geo_animation.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.util.math.Vec3d;

public record GeoPosition(
        Vec3d position
) {

    public static final Codec<GeoPosition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3d.CODEC.fieldOf("position").forGetter(null)
    ).apply(instance, GeoPosition::new));

    public Keyframe toKeyframe(float timestamp) {
        return new Keyframe(
                timestamp,
                AnimationHelper.createTranslationalVector(
                        (float)position.x, (float)position.y, (float)position.z
                ),
                Transformation.Interpolations.LINEAR
        );
    }

}
