package net.insomniacs.golgi.test;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public record ModRegistry(
		String modId
) {

	private <T> T register(Registry<T> registry, String id, T value) {
		return Registry.register(registry, of(id), value);
	}

	private Identifier of(String path) {
		return new Identifier(modId, path);
	}

	public Item item(String id, Item item) {
		return register(Registries.ITEM, id, item);
	}

	public Block block(String id, Block block) {
		return register(Registries.BLOCK, id, block);
	}

}
