package net.insomniacs.nucleus.api.items;

import net.insomniacs.nucleus.api.components.NucleusBundleComponent;
import net.minecraft.item.tooltip.TooltipData;

public record NucleusBundleTooltipData(NucleusBundleComponent component) implements TooltipData {}
