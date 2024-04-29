package net.insomniacs.nucleus.api.animationLoader;

import com.google.gson.JsonElement;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.dataLoader.SimpleFileLoader;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class NucleusAnimationLoader implements SimpleFileLoader {

	@Override
	public Identifier getFabricId() {
		return Nucleus.of("entity_animations");
	}
	public static final NucleusAnimationLoader INSTANCE = new NucleusAnimationLoader();

	private final Map<Identifier, AnimationContainer> animations;
	private NucleusAnimationLoader() {
		this.animations = new HashMap<>();
	}

	@Override
	public void init(DataFileLoader loader, ResourceManager manager) {
		loader.json().findGlob("animations", "**/*.animation.json", this::parseAnimationFile);
	}

	public void parseAnimationFile(Identifier id, JsonElement data) {
		Identifier finalId = id.withPath(path ->
				path.split("animations/")[1].split(".animation.json")[0]
		);
		AnimationContainer container = AnimationContainer.fromJson(data);
		addEntry(finalId, container);
	}

	public static void addEntry(Identifier id, AnimationContainer container) {
		INSTANCE.animations.put(id, container);
	}

	public static AnimationContainer getEntry(Identifier id) {
		return INSTANCE.animations.get(id);
	}

	public static AnimationContainer getEntity(EntityType<?> entity) {
		Identifier id = Registries.ENTITY_TYPE.getId(entity).withPrefixedPath("entity/");
		return getEntry(id);
	}

	public static AnimationContainer getBlock(Block block) {
		Identifier id = Registries.BLOCK.getId(block).withPrefixedPath("block/");
		return getEntry(id);
	}

}
