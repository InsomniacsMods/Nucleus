package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.insomniacs.nucleus.api.geo_animation.GeoAnimationLoader;
import net.insomniacs.nucleus.api.geo_model.GeoModelLoader;
import net.insomniacs.nucleus.impl.entities.*;
import net.insomniacs.nucleus.impl.misc.NucleusCreativeModeTabs;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class NucleusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(GeoModelLoader.INSTANCE);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(GeoAnimationLoader.INSTANCE);

		registerVilger();

		NucleusItemPredicates.registerPredicates();

		boolean dev = FabricLoader.getInstance().isDevelopmentEnvironment();
		if (dev) NucleusCreativeModeTabs.init();
	}

	public void registerVilger() {
		Identifier vilgerEntityId = Nucleus.id("spook");
		EntityModelLayer vilgerModelLayer = new EntityModelLayer(vilgerEntityId, "main");
		EntityModelLayerRegistry.registerModelLayer(
				vilgerModelLayer,
				() -> GeoModelLoader.getEntity(vilgerEntityId)
		);
		EntityRendererRegistry.register(
				NucleusEntities.VILGER,
				(context) -> new VilgerEntityRenderer(context, vilgerModelLayer, vilgerEntityId)
		);
	}

}