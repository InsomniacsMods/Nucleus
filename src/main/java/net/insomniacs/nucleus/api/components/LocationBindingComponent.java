package net.insomniacs.nucleus.api.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.api.utils.Location;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public record LocationBindingComponent (
		Location location,
		boolean showInTooltip
) implements TooltipAppender {

	public static final LocationBindingComponent EMPTY = new LocationBindingComponent(Location.ZERO, false);

	public static final Codec<LocationBindingComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Location.CODEC.fieldOf("location").forGetter(LocationBindingComponent::location),
			Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter(LocationBindingComponent::showInTooltip)
	).apply(instance, LocationBindingComponent::new));

	public static LocationBindingComponent simple(Location location) {
		return new LocationBindingComponent(location, true);
	}

	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
		if (showInTooltip) tooltip.accept(getTooltip());
	}

	public Text getTooltip() {
		return Text.translatable("item.nucleus.bound_location",
				location.dimensionName(),
				location.x(), location.y(), location.z()
		).formatted(Formatting.GRAY);
	}

}
