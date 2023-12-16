package net.insomniacs.nucleus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.insomniacs.nucleus.api.blockbench.BBEntityDataLoader;
import net.insomniacs.nucleus.api.geo.GeoEntityModel;
import net.insomniacs.nucleus.api.geo.GeoModelLoader;
import net.insomniacs.nucleus.impl.entities.NucleusEntities;
import net.insomniacs.nucleus.impl.entities.VilgerEntityRenderer;
import net.insomniacs.nucleus.impl.misc.NucleusCreativeModeTabs;
import net.minecraft.resource.ResourceType;

public class NucleusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
//		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(BBEntityDataLoader.INSTANCE);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(GeoModelLoader.INSTANCE);
		EntityRendererRegistry.register(NucleusEntities.VILGER, VilgerEntityRenderer::new);

		NucleusItemPredicates.registerPredicates();
		boolean dev = FabricLoader.getInstance().isDevelopmentEnvironment();
//		boolean operatorItems = MinecraftClient.getInstance().options.getOperatorItemsTab().getValue();
		if (dev) { // && operatorItems) {
			NucleusCreativeModeTabs.init();
		}
	}

}