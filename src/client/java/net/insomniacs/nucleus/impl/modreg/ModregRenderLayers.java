package net.insomniacs.nucleus.impl.modreg;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.ModEntry;
import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.insomniacs.nucleus.api.modreg.entries.BlockEntry;
import net.insomniacs.nucleus.api.modreg.entries.ItemEntry;
import net.insomniacs.nucleus.api.modreg.utils.BlockRenderLayers;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ModregRenderLayers {

	public static void process() {
		ModRegistry.getEntries()
				.stream()
				.filter(entry -> entry.getType().equals(Nucleus.id("block")))
				.map(entry -> (BlockEntry)entry)
				.forEach(ModregRenderLayers::processBlock);
	}

	private static void processBlock(BlockEntry entry) {
		entry.getRenderLayers()
				.stream()
				.map(ModregRenderLayers::getLayer)
				.forEach(layer -> BlockRenderLayerMap.INSTANCE.putBlocks(layer, entry.value()));
	}

	private static RenderLayer getLayer(BlockRenderLayers layer) {
		return switch (layer) {
			case CUTOUT -> RenderLayer.getCutout();
			case CUTOUT_MIPMAP -> RenderLayer.getCutoutMipped();
			case TRANSLUCENT -> RenderLayer.getTranslucent();
			default -> RenderLayer.getSolid();
		};
	}

}
