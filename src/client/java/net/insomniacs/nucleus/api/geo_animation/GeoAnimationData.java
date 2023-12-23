package net.insomniacs.nucleus.api.geo_animation;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.Nucleus;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public record GeoAnimationData(
        Map<String, GeoAnimation> animations
) {

    @Nullable
    public static GeoAnimationData fromJson(@Nullable Identifier identifier, JsonObject object) {
        Consumer<String> logError = message -> Nucleus.LOGGER.error(String.format("Error loading Animation '%s': " + message, identifier));
        return CODEC.parse(JsonOps.INSTANCE, object).getOrThrow(true, logError);
    }

    public static final Codec<GeoAnimationData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, GeoAnimation.CODEC).fieldOf("animations").forGetter(null)
    ).apply(instance, GeoAnimationData::new));

    public Map<String, Animation> getAnimationMap() {
        Map<String, Animation> animationMap = new HashMap<>();
        this.animations.forEach((name, animationData) -> animationMap.put(name, animationData.toAnimation()));
        return animationMap;
    }

}
