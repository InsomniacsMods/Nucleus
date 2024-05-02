package net.insomniacs.nucleus.api.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.TooltipAppender;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Formatting;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public record InfoTooltipComponent (
		Text... tooltips
) implements TooltipAppender {

	public static final Codec<Text[]> TEXT_ARRAY_CODEC = TextCodecs.CODEC.listOf()
			.xmap(InfoTooltipComponent::toArray, List::of);

	public static final Codec<InfoTooltipComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			TEXT_ARRAY_CODEC.fieldOf("tooltips").forGetter(InfoTooltipComponent::tooltips)
	).apply(instance, InfoTooltipComponent::new));

	public static Text[] toArray(List<Text> list) {
		return list.toArray(Text[]::new);
	}


	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
		if (!type.isAdvanced()) {
			var text = Text.literal("Hold Shift for more information...");
			text = text.formatted(Formatting.GRAY).styled(style -> style.withItalic(true));
			tooltip.accept(text);
			return;
		}
		Arrays.stream(tooltips).forEach(tooltip);
	}

}
