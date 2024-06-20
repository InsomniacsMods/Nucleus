package net.insomniacs.nucleus.api.components;


import net.minecraft.component.ComponentType;
import net.minecraft.item.tooltip.TooltipAppender;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ComponentTooltipRegistry {

	private static final List<ComponentType<? extends TooltipAppender>> COMPONENTS = new LinkedList<>();

	public static List<ComponentType<? extends TooltipAppender>> getComponents() {
		return COMPONENTS;
	}

	public static void register(ComponentType<? extends TooltipAppender> component) {
		COMPONENTS.add(component);
	}

	@SafeVarargs
	public static void register(ComponentType<? extends TooltipAppender>... components) {
		Arrays.stream(components).forEach(ComponentTooltipRegistry::register);
	}

}
