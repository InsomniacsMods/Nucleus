package net.insomniacs.nucleus.api.components;

import net.minecraft.component.DataComponentType;
import net.minecraft.item.TooltipAppender;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ComponentTooltipRegistry {

	private static final List<DataComponentType<? extends TooltipAppender>> COMPONENTS = new LinkedList<>();

	public static List<DataComponentType<? extends TooltipAppender>> getComponents() {
		return COMPONENTS;
	}

	public static void register(DataComponentType<? extends TooltipAppender> component) {
		COMPONENTS.add(component);
	}

	@SafeVarargs
	public static void register(DataComponentType<? extends TooltipAppender>... components) {
		Arrays.stream(components).forEach(ComponentTooltipRegistry::register);
	}

}
