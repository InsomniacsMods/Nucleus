package net.insomniacs.nucleus.api.components;


import net.minecraft.component.ComponentType;
import net.minecraft.item.tooltip.TooltipAppender;

import java.util.LinkedList;
import java.util.List;

public class NucleusComponentTooltipRegistry {

	private static final NucleusComponentTooltipRegistry INSTANCE = new NucleusComponentTooltipRegistry();

	private final List<ComponentType<? extends TooltipAppender>> components;

	private NucleusComponentTooltipRegistry() {
		this.components = new LinkedList<>();
	}


	public static void register(ComponentType<? extends TooltipAppender> component) {
		INSTANCE.components.add(component);
	}

	@SafeVarargs
	public static void register(ComponentType<? extends TooltipAppender>... components) {
		for (ComponentType<? extends TooltipAppender> component : components) register(component);
	}


	public static List<ComponentType<? extends TooltipAppender>> getComponents() {
		return INSTANCE.components;
	}

}
