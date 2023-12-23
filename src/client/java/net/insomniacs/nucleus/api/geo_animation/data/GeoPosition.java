package net.insomniacs.nucleus.api.geo_animation.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.utils.Vec3f;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public record GeoPosition(
        Vec3f position
) {

    public static final Codec<GeoPosition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3f.CODEC.fieldOf("position").forGetter(null)
    ).apply(instance, GeoPosition::new));

    public Keyframe toKeyframe(float timestamp) {
        return new Keyframe(
                timestamp,
                AnimationHelper.createTranslationalVector(position.x, position.y, position.z),
                Transformation.Interpolations.LINEAR
        );
    }

}
