package net.insomniacs.golgi.test.envelope;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.LinkedList;
import java.util.List;

public class EnvelopeAPI {

	private static final EnvelopeAPI INSTANCE = new EnvelopeAPI();
	private EnvelopeAPI() {}


	private final List<ModMetadata> registeredMods = new LinkedList<>();



	public static void registerMod(ModMetadata data) {
		INSTANCE.registeredMods.add(data);
	}

	public static void registerMod(ModContainer container) {
		registerMod(container.getMetadata());
	}

	public static void registerMod(String id) {
		FabricLoader.getInstance()
				.getModContainer(id)
				.ifPresent(EnvelopeAPI::registerMod);
	}


	public static boolean isModRegistered(String id) {
		for (ModMetadata data : INSTANCE.registeredMods) {
			if (data.getId().equals(id)) return true;
		}
		return false;
	}

	public static boolean isModRegistered(RegistryEntry.Reference<?> entry) {
		return isModRegistered(entry.registryKey().getValue().getNamespace());
	}

}
