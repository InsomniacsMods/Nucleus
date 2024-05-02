package net.insomniacs.nucleus.api.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.TooltipAppender;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.function.Consumer;

public record SoulboundComponent (
		boolean showInTooltip
) implements TooltipAppender {

	public static final SoulboundComponent SIMPLE = new SoulboundComponent(true);

	public static final Codec<SoulboundComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter(SoulboundComponent::showInTooltip)
	).apply(instance, SoulboundComponent::new));

	private static final int COLOR = Color.decode("#A9CCCA").getRGB();
	public static final Text TOOLTIP = Text.translatable("item.nucleus.soulbound")
			.styled(style -> style.withItalic(true).withColor(COLOR));

	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
		if (showInTooltip) tooltip.accept(TOOLTIP);
	}

}
