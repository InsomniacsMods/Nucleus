package net.insomniacs.nucleus.mixin;

import net.insomniacs.nucleus.api.NucleusControlsRegistry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsOptionsScreen.class)
public abstract class ControlsOptionsScreenMixin extends GameOptionsScreen {

	public ControlsOptionsScreenMixin(Screen parent, GameOptions options, Text title) {
		super(parent, options, title);
	}

	@Inject(method = "addOptions", at = @At("TAIL"))
	private void addCustomOptions(CallbackInfo ci) {
		if (this.body == null) return;
		System.out.println(NucleusControlsRegistry.getOptions());
        this.body.addAll(NucleusControlsRegistry.getOptions().toArray(SimpleOption<?>[]::new));
	}

}