package net.insomniacs.nucleus.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BlockStatesLoader;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(BlockStatesLoader.class)
public interface BlockStatesLoaderAccessor {

	@Accessor("STATIC_DEFINITIONS")
	static Map<Identifier, StateManager<Block, BlockState>> getStaticDefinitions() {
		throw new UnsupportedOperationException();
	}

}
