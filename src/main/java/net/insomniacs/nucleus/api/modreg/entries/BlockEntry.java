package net.insomniacs.nucleus.api.modreg.entries;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.SettingsModEntry;
import net.insomniacs.nucleus.api.modreg.utils.BlockLootTableEntry;
import net.insomniacs.nucleus.api.modreg.utils.BlockModelEntry;
import net.insomniacs.nucleus.api.modreg.utils.BlockRenderLayers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

public class BlockEntry extends SettingsModEntry<BlockEntry, BlockEntry.Builder, Block.Settings, Block> {

	@Override
	public Registry<Block> getRegistry() {
		return Registries.BLOCK;
	}

	@Nullable
	private ItemEntry item;

	public BlockEntry(Builder settings) {
		super(settings);

		if (settings.itemBuilder != null) this.item = settings.itemBuilder
				.setConstructor(itemSettings -> settings.itemConstructor.apply(value, itemSettings))
				.register();
	}

	public @Nullable ItemEntry getItem() {
		return this.item;
	}

	public BlockModelEntry getModel() {
		return this.settings.model;
	}

	public BlockLootTableEntry getDrops() {
		return this.settings.drops;
	}

	public List<BlockRenderLayers> getRenderLayers() {
		return this.settings.renderLayers;
	}

	public static class Builder extends SettingsModEntry.EntryBuilder<Builder, BlockEntry, Block.Settings, Block> {

		public Builder(Identifier id, Function<AbstractBlock.Settings, Block> constructor) {
			super(id, constructor);
		}

		public Builder(Identifier id) {
			super(id);
		}

		@Override
		protected Block.Settings generateSettings() {
			return FabricBlockSettings.create();
		}

		@Override
		protected BlockEntry createEntry() {
			return new BlockEntry(this);
		}

		// Settings

		public Builder instrument(Instrument instrument) {
			return modifySettings(settings -> settings.instrument(instrument));
		}

		public Builder requiresTool() {
			return modifySettings(AbstractBlock.Settings::requiresTool);
		}
        public Builder requiresSword() {
            return requiresTool().tag(BlockTags.SWORD_EFFICIENT);
        }
        public Builder requiresShovel() {
            return requiresTool().tag(BlockTags.SHOVEL_MINEABLE);
        }
        public Builder requiresAxe() {
            return requiresTool().tag(BlockTags.AXE_MINEABLE);
        }
        public Builder requiresPickaxe() {
            return requiresTool().tag(BlockTags.PICKAXE_MINEABLE);
        }
        public Builder requiresHoe() {
            return requiresTool().tag(BlockTags.HOE_MINEABLE);
        }

		public Builder strength(float hardness, float resistance) {
			return modifySettings(settings -> settings.hardness(hardness).resistance(resistance));
		}
		public Builder hardness(float value) {
			return modifySettings(settings -> settings.hardness(value));
		}
		public Builder resistance(float value) {
			return modifySettings(settings -> settings.resistance(value));
		}

		public Builder fragile() {
			return modifySettings(AbstractBlock.Settings::breakInstantly);
		}

		public Builder mapColor(MapColor color) {
			return modifySettings(settings -> settings.mapColor(color));
		}

        public Builder sounds(BlockSoundGroup group) {
            return modifySettings(settings -> settings.sounds(group));
        }

		public Builder luminance(ToIntFunction<BlockState> valueFunction) {
			return modifySettings(settings -> settings.luminance(valueFunction));
		}

		public Builder luminance(int value) {
			return luminance(n -> value);
		}

		public Builder burnable() {
			return modifySettings(AbstractBlock.Settings::burnable);
		}

		// Item

		private ItemEntry.Builder itemBuilder;
		private BiFunction<Block, Item.Settings, BlockItem> itemConstructor;

		public Builder defaultItem(Identifier id, BiFunction<Block, Item.Settings, BlockItem> constructor) {
			this.itemBuilder = new ItemEntry.Builder(id);
			this.itemConstructor = constructor;
			return this;
		}

		public Builder defaultItem(String name, BiFunction<Block, Item.Settings, BlockItem> constructor) {
			return defaultItem(this.id.withPath(name), constructor);
		}

		public Builder defaultItem(BiFunction<Block, Item.Settings, BlockItem> constructor) {
			return defaultItem(this.id, constructor);
		}

		public Builder defaultItem() {
			return defaultItem(this.id, BlockItem::new);
		}

		public Builder defaultItem(UnaryOperator<ItemEntry.Builder> modifier) {
			return defaultItem().modifyItem(modifier);
		}

		public Builder modifyItem(UnaryOperator<ItemEntry.Builder> modifier) {
			this.itemBuilder = modifier.apply(itemBuilder);
			return this;
		}

		// Model

		private final BlockModelEntry model = new BlockModelEntry();

		public Builder model(Model model, TextureMap textures) {
			this.model.generateBlock(model, textures);
			return this;
		}

		public Builder model(Model model) {
			return model(model, TextureMap.all(this.id.withPrefixedPath("block/")));
		}

		public Builder model() {
			return model(Models.CUBE_ALL);
		}

		public Builder pillarModel() {
			TextureMap map = new TextureMap()
					.put(TextureKey.SIDE, id.withSuffixedPath("_side"))
					.put(TextureKey.TOP, id.withSuffixedPath("_top"));
			return model(Models.CUBE_COLUMN, map);
		}

		public Builder itemModel(String suffix) {
			this.model.generateItem(suffix);
			return this;
		}

		public Builder itemModel() {
			return itemModel("");
		}

		// Loot Table

		private BlockLootTableEntry drops;

		public Builder drops(ItemConvertible item) {
			this.drops = new BlockLootTableEntry(item);
			return this;
		}

		public Builder dropsSelf() {
			if (this.itemBuilder == null) {
				Nucleus.LOGGER.error("Error registering Loot Table for Block '%s': No Item registered".formatted(this.id));
				return this;
			}
			this.drops = new BlockLootTableEntry(true);
			return this;
		}

		// Render Layer

		private final List<BlockRenderLayers> renderLayers = new ArrayList<>();

		public Builder renderLayer(BlockRenderLayers layer) {
			this.renderLayers.add(layer);
			return this;
		}

		// Utils

		public boolean validateItem() {
			if (this.itemBuilder == null) {
				Nucleus.LOGGER.error("Error registering Loot Table for Block '%s': No Item registered".formatted(this.id));
				return false;
			}
			return true;
		}

	}

}
