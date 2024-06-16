package net.insomniacs.nucleus.api.items;

import net.insomniacs.nucleus.api.components.NucleusBundleComponent;
import net.minecraft.client.item.TooltipData;

public record NucleusBundleTooltipData(NucleusBundleComponent component) implements TooltipData {}
