package net.insomniacs.nucleus.mixin.custom_capes;

import net.insomniacs.nucleus.asm.NucleusGameOptions;
import net.insomniacs.nucleus.impl.custom_capes.CapeManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(GameOptions.class)
public class GameOptionsMixin implements NucleusGameOptions {

	@Override
	public Identifier getCapeTexture() {
		return this.capeTexture.getValue();
	}

	@Override
	public void setCapeTexture(Identifier id) {
		this.capeTexture.setValue(id);
	}


	@Unique
	private static final Text TOOLTIP = Text.translatable("options.cape.texture");

	@Unique
	private final SimpleOption<Identifier> capeTexture = new SimpleOption<>(
			"options.cape.texture",
			SimpleOption.constantTooltip(TOOLTIP),
			this::translateCape,
			new SimpleOption.LazyCyclingCallbacks<>(
					CapeManager::getPossibleCapes, Optional::ofNullable, Identifier.CODEC
			),
			CapeManager.POSSIBLE_CAPES.getFirst(),
			v -> {}
	);

	@Unique
	private Text translateCape(Text optionText, Identifier value) {
		System.out.println(optionText);
		System.out.println(value);
		return Text.translatable(value.toTranslationKey("cape"));
	}

	@Inject(method = "accept", at = @At("TAIL"))
	private void addOptions(GameOptions.Visitor visitor, CallbackInfo ci) {
		visitor.accept("toolTipOpacity", this.capeTexture);
	}

}
