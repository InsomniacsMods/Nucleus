package net.insomniacs.nucleus.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(ModelLoader.class)
public class ModelLoaderMixin {

	@Shadow @Mutable @Final private static Map<Identifier, StateManager<Block, BlockState>> STATIC_DEFINITIONS;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void addDefaultBlockstates(CallbackInfo ci) {
		STATIC_DEFINITIONS = new HashMap<>(STATIC_DEFINITIONS);
	}

}
