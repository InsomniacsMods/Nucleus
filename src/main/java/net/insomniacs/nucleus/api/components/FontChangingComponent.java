package net.insomniacs.nucleus.api.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public record FontChangingComponent(
		Identifier font,
		boolean showInTooltip
) implements TooltipAppender {

	public static final FontChangingComponent EMPTY = new FontChangingComponent(Identifier.of(""), false);

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
		return Text.translatable("item.nucleus.font_changing", useButton, getFontNameStyled()).formatted(Formatting.GRAY);
	}

	public MutableText getFontName() {
		return Text.translatable(font.toTranslationKey("font"));
	}

	public MutableText getFontNameStyled() {
		return getFontName().styled(t -> t.withFont(font));
	}

}
