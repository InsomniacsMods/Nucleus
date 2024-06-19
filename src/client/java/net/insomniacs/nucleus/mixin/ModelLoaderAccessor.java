package net.insomniacs.nucleus.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ModelLoader.class)
public interface ModelLoaderAccessor {

	@Accessor("STATIC_DEFINITIONS")
	static Map<Identifier, StateManager<Block, BlockState>> getStaticDefinitions() {
		throw new UnsupportedOperationException();
	}

}
