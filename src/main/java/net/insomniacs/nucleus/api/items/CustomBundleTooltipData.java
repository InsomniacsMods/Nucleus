package net.insomniacs.nucleus.api.items;

import net.insomniacs.nucleus.api.components.BundleComponent;
import net.minecraft.client.item.TooltipData;

public record CustomBundleTooltipData(BundleComponent component) implements TooltipData {}
