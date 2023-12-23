package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.insomniacs.nucleus.api.geo_animation.GeoAnimationLoader;
import net.insomniacs.nucleus.api.geo_model.GeoModelLoader;
import net.insomniacs.nucleus.impl.entities.NucleusEntities;
import net.insomniacs.nucleus.impl.entities.VilgerEntityModel;
import net.insomniacs.nucleus.impl.entities.VilgerEntityRenderer;
import net.insomniacs.nucleus.impl.misc.NucleusCreativeModeTabs;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

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
		Identifier vilgerEntityId = Nucleus.id("vilger");
		EntityModelLayer vilgerModelLayer = new EntityModelLayer(vilgerEntityId, "main");
		EntityModelLayerRegistry.registerModelLayer(
				vilgerModelLayer,
				() -> VilgerEntityModel.getModelData(vilgerEntityId)
		);
		EntityRendererRegistry.register(
				NucleusEntities.VILGER,
				(context) -> new VilgerEntityRenderer(context, vilgerModelLayer, vilgerEntityId)
		);
	}

}