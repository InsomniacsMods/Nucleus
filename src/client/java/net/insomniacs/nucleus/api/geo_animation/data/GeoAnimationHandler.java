package net.insomniacs.nucleus.api.geo_animation.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.entity.animation.Keyframe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeoAnimationHandler {

    public final Map<Float, GeoRotation> rotations;
    public final Map<Float, GeoPosition> positions;
    public final Map<Float, GeoScale> scales;

    public GeoAnimationHandler(
            Map<String, GeoRotation> rotations,
            Map<String, GeoPosition> positions,
            Map<String, GeoScale> scales
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

    public static final Codec<GeoAnimationHandler> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, GeoRotation.CODEC).fieldOf("rotation").forGetter(null),
            Codec.unboundedMap(Codec.STRING, GeoPosition.CODEC).fieldOf("position").forGetter(null),
            Codec.unboundedMap(Codec.STRING, GeoScale.CODEC).fieldOf("scale").forGetter(null)
    ).apply(instance, GeoAnimationHandler::new));

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
