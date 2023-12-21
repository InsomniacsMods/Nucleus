package net.insomniacs.nucleus.api.geo_anim.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.entity.animation.Keyframe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoneAnimation {

    public final Map<Float, BoneRotation> rotations;

    public BoneAnimation(
            Map<String, BoneRotation> rotations
    ) {
        this.rotations = new HashMap<>();
        rotations.forEach((timestampString, rotation) -> {
            float timestamp = Float.parseFloat(timestampString);
            this.rotations.put(timestamp, rotation);
        });
    }

    public static final Codec<BoneAnimation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, BoneRotation.CODEC).fieldOf("rotation").forGetter(null)
    ).apply(instance, BoneAnimation::new));

    public List<Keyframe> toKeyframes() {
        List<Keyframe> keyframes = new ArrayList<>();
        this.rotations.forEach((timestamp, rotation) -> {
            Keyframe keyframe = rotation.toKeyframe(timestamp);
            keyframes.add(keyframe);
        });
        return keyframes;
    }

}
