package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.insomniacs.nucleus.api.crosshair.CrosshairTextureCallback;
import net.insomniacs.nucleus.api.items.CustomBundleItem;
import net.insomniacs.nucleus.api.items.LocationBindingItem;
import net.insomniacs.nucleus.api.tooltipLoader.ModTooltipLoader;
import net.insomniacs.nucleus.impl.NucleusItemPredicates;
import net.insomniacs.nucleus.impl.splashTexts.SplashTextLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Optional;

public class NucleusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		NucleusItemPredicates.addProvider(LocationBindingItem.class, "has_location", LocationBindingItem::getLocationPredicate);
		NucleusItemPredicates.addProvider(CustomBundleItem.class, "filled", CustomBundleItem::getOccupancyPredicate);

		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(SplashTextLoader.INSTANCE);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(ModTooltipLoader.INSTANCE);
//		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(NucleusAnimationLoader.INSTANCE);

		CrosshairTextureCallback.EVENT.register(NucleusClient::getItemCrosshairs);

		NucleusItemPredicates.register();

	}

	public static Optional<Identifier> getItemCrosshairs(World world, PlayerEntity player) {
		for (ItemStack stack : player.getHandItems()) {
			var texture = stack.getItem().crosshairTexture(world, player);
			if (texture.isPresent()) return texture;
		}
		return player.getActiveItem().getItem().crosshairTexture(world, player);
	}

}