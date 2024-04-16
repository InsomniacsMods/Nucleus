package net.insomniacs.nucleus.api.animationLoader;

import com.google.gson.JsonElement;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.animationLoader.types.AnimationData;
import net.insomniacs.nucleus.api.dataFileLoader.SimpleFileLoader;
import net.insomniacs.nucleus.impl.splashTexts.SplashTextLoader;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class NucleusAnimationLoader implements SimpleFileLoader {

	@Override
	public Identifier getFabricId() {
		return Nucleus.id("entity_animations");
	}
	public static final NucleusAnimationLoader INSTANCE = new NucleusAnimationLoader();

	private static final Map<Identifier, AnimationContainer> ANIMATIONS = new HashMap<>();

	public static AnimationContainer getEntry(Identifier id) {
		return ANIMATIONS.get(id);
	}

	public static AnimationContainer getEntity(EntityType<?> entity) {
		Identifier id = Registries.ENTITY_TYPE.getId(entity).withPrefixedPath("entity/");
		return getEntry(id);
	}

	public static void addEntry(Identifier id, AnimationContainer container) {
		ANIMATIONS.put(id, container);
	}

	@Override
	public void init(DataFileLoader loader, ResourceManager manager) {
		loader.json().findAllMatching("animations", "**/*.animation.json", this::readAnimationFile);
	}

	public void readAnimationFile(Identifier id, JsonElement data) {
		Identifier entityID = id.withPath(path ->
				path.split("animations/")[1].split(".animation.json")[0]
		);
		addEntry(entityID, AnimationContainer.fromJson(data));
		System.out.println(getEntry(entityID));
	}

}
