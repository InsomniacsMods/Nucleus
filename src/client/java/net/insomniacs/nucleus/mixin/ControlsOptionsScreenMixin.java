package net.insomniacs.nucleus.mixin;

import net.insomniacs.nucleus.api.NucleusControlsRegistry;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;

@Mixin(ControlsOptionsScreen.class)
public class ControlsOptionsScreenMixin {

	@Inject(method = "getOptions", at = @At("RETURN"), cancellable = true)
	private static void addOptions(GameOptions gameOptions, CallbackInfoReturnable<SimpleOption<?>[]> cir) {
		SimpleOption<?>[] options = cir.getReturnValue();
		List<SimpleOption<?>> newOptions = Arrays.asList(options);
		newOptions.addAll(NucleusControlsRegistry.getOptions());
		NucleusControlsRegistry.clear();
		SimpleOption<?>[] result = newOptions.toArray(SimpleOption<?>[]::new);
		cir.setReturnValue(result);
	}

}