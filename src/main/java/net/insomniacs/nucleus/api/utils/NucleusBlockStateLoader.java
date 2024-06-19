package net.insomniacs.nucleus.api.utils;

import net.insomniacs.sanguine.mixin.ModelLoaderAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;

public class NucleusBlockStateLoader {

	public static void putStateManager(Identifier id, StateManager<Block, BlockState> manager) {
		ModelLoaderAccessor.getStaticDefinitions().put(id, manager);
	}

}
