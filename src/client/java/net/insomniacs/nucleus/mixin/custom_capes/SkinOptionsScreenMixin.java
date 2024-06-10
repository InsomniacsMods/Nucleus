package net.insomniacs.nucleus.mixin.custom_capes;

import com.llamalad7.mixinextras.sugar.Local;
import net.insomniacs.nucleus.asm.NucleusGameOptions;
import net.insomniacs.nucleus.impl.custom_capes.CapeManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SkinOptionsScreen.class)
public class SkinOptionsScreenMixin extends GameOptionsScreen {

	public SkinOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
		super(parent, gameOptions, title);
	}

	@Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/OptionListWidget;addAll(Ljava/util/List;)V"))
	private void injected(CallbackInfo ci, @Local List<ClickableWidget> widgets) {
		NucleusGameOptions options = (NucleusGameOptions)this.gameOptions;
		var widget = CyclingButtonWidget.<Identifier>builder(test -> Text.empty())
				.values(CapeManager.POSSIBLE_CAPES)
				.build(
						Text.translatable("options.cape.texture"),
						(button, enabled) -> options.setCapeTexture(enabled)
				);
		widgets.add(widget);
	}

}
