package net.insomniacs.nucleus.api.components.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.TooltipAppender;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public record FontChangingComponent(
		Identifier font,
		boolean showInTooltip
) implements TooltipAppender {

	public static final FontChangingComponent EMPTY = new FontChangingComponent(new Identifier(""), false);

	public static final Codec<FontChangingComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Identifier.CODEC.fieldOf("location").forGetter(FontChangingComponent::font),
			Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter(FontChangingComponent::showInTooltip)
	).apply(instance, FontChangingComponent::new));


	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
		if (showInTooltip) tooltip.accept(getTooltip());
	}

	public Text getTooltip() {
		Text useButton = Text.keybind("key.use");
		return Text.translatable("ui.nucleus.font_changing", useButton, getFontNameStyled());
	}

	private MutableText getFontName() {
		return Text.translatable(font.toTranslationKey("font"));
	}

	private MutableText getFontNameStyled() {
		return getFontName().styled(t -> t.withFont(font));
	}

}
