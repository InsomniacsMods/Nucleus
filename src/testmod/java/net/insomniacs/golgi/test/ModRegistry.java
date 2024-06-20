package net.insomniacs.golgi.test;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.component.ComponentType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ModRegistry {

	private String id;
	private ModMetadata data;

	public ModRegistry(
			String id
	) {
		this.id = id;
		this.data = FabricLoader.getInstance().getModContainer(id).orElseThrow().getMetadata();
	}

	private Identifier of(String path) {
		return Identifier.of(id, path);
	}


	// Item

	public Item item(String id, Item item) {
		return Registry.register(Registries.ITEM, of(id), item);
	}

	public Item item(String id, Item.Settings settings) {
		return item(id, new Item(settings));
	}

	public Item item(String id, Function<Item.Settings, Item> function) {
		return item(id, function.apply(new Item.Settings()));
	}

	public Item item(String id) {
		return item(id, new Item.Settings());
	}

	public Item item(String id, Block block, Item.Settings settings) {
		return item(id, new BlockItem(block, settings));
	}

	public Item item(String id, Block block) {
		return item(id, new BlockItem(block, new Item.Settings()));
	}



	// Block

	public Block block(String id, Block block) {
		return Registry.register(Registries.BLOCK, of(id), block);
	}

	public Block block(String id, AbstractBlock.Settings settings) {
		return block(id, new Block(settings));
	}

	public Block block(String id, Function<AbstractBlock.Settings, Block> function) {
		return block(id, function.apply(AbstractBlock.Settings.create()));
	}

	public Block block(String id) {
		return block(id, AbstractBlock.Settings.create());
	}


	// Component

	public <T> ComponentType<T> component(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return Registry.register(
				Registries.DATA_COMPONENT_TYPE,
				of(id),
				builderOperator.apply(ComponentType.builder()).cache().build()
		);
	}

	public <T> ComponentType<T> component(String id, Codec<T> codec, PacketCodec<ByteBuf, T> packetCodec) {
		return component(id, builder -> builder.codec(codec).packetCodec(packetCodec));
	}

	public <T> ComponentType<T> component(String id, Codec<T> codec) {
		return component(id, codec, PacketCodecs.codec(codec));
	}

}
