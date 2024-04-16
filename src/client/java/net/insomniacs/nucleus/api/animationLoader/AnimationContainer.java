package net.insomniacs.nucleus.api.animationLoader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.animationLoader.types.AnimationData;
import net.minecraft.client.render.entity.animation.Animation;

import java.util.HashMap;
import java.util.Map;

public class AnimationContainer {

	public static final UnboundedMapCodec<String, AnimationData> MAP_CODEC = Codec.unboundedMap(
			Codec.STRING,
			AnimationData.CODEC
	);

	public static final Codec<AnimationContainer> CODEC = MAP_CODEC.flatComapMap(AnimationContainer::new, null);


	private final Map<String, Animation> animations = new HashMap<>();

	private AnimationContainer(Map<String, AnimationData> animations) {
		animations.forEach(this::addAnimation);
	}

	public Animation getAnimation(String name) {
		return animations.get(name);
	}

	private void addAnimation(String name, AnimationData data) {
		this.animations.put(name, data.toAnimation());
	}


	public static AnimationContainer fromJson(JsonElement data) {
		JsonObject animations = data.getAsJsonObject().getAsJsonObject("animations");
		return CODEC.parse(JsonOps.INSTANCE, animations).getOrThrow(true, Nucleus.LOGGER::error);
	}

}
