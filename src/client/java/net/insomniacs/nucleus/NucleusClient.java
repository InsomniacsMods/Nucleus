package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.insomniacs.nucleus.api.items.CustomBundleItem;
import net.insomniacs.nucleus.api.items.LocationBindingItem;
import net.insomniacs.nucleus.api.tooltipLoader.ModTooltipLoader;
import net.insomniacs.nucleus.impl.NucleusItemPredicates;
import net.insomniacs.nucleus.impl.splashTexts.SplashTextLoader;
import net.minecraft.resource.ResourceType;

public class NucleusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		NucleusItemPredicates.addProvider(LocationBindingItem.class, "has_location", LocationBindingItem::getLocationPredicate);
		NucleusItemPredicates.addProvider(CustomBundleItem.class, "filled", CustomBundleItem::getAmountFilled);

		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(SplashTextLoader.INSTANCE);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(ModTooltipLoader.INSTANCE);
//		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(NucleusAnimationLoader.INSTANCE);

		NucleusItemPredicates.register();

	}

}