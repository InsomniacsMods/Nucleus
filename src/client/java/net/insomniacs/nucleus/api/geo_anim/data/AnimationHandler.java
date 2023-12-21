package net.insomniacs.nucleus.api.geo_anim.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.api.geo_anim.data.types.BonePosition;
import net.insomniacs.nucleus.api.geo_anim.data.types.BoneRotation;
import net.insomniacs.nucleus.api.geo_anim.data.types.BoneScale;
import net.minecraft.client.render.entity.animation.Keyframe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationHandler {

    public final Map<Float, BoneRotation> rotations;
    public final Map<Float, BonePosition> positions;
    public final Map<Float, BoneScale> scales;

    public AnimationHandler (
            Map<String, BoneRotation> rotations,
            Map<String, BonePosition> positions,
            Map<String, BoneScale> scales
    ) {
        this.rotations = remapTimestamps(rotations);
        this.positions = remapTimestamps(positions);
        this.scales = remapTimestamps(scales);
    }

    public <T> Map<Float, T> remapTimestamps(Map<String, T> map) {
        Map<Float, T> result = new HashMap<>();
        map.forEach((timestampString, value) -> {
            float timestamp = Float.parseFloat(timestampString);
            result.put(timestamp, value);
        });
        return result;
    }

    public static final Codec<AnimationHandler> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, BoneRotation.CODEC).fieldOf("rotation").forGetter(null),
            Codec.unboundedMap(Codec.STRING, BonePosition.CODEC).fieldOf("position").forGetter(null),
            Codec.unboundedMap(Codec.STRING, BoneScale.CODEC).fieldOf("scale").forGetter(null)
    ).apply(instance, AnimationHandler::new));

    public List<Keyframe> toKeyframes() {
        List<Keyframe> keyframes = new ArrayList<>();
        this.rotations.forEach((timestamp, rotation) -> {
            Keyframe keyframe = rotation.toKeyframe(timestamp);
            keyframes.add(keyframe);
        });
        this.positions.forEach((timestamp, position) -> {
            Keyframe keyframe = position.toKeyframe(timestamp);
            keyframes.add(keyframe);
        });
        this.scales.forEach((timestamp, scale) -> {
            Keyframe keyframe = scale.toKeyframe(timestamp);
            keyframes.add(keyframe);
        });
        return keyframes;
    }

}
