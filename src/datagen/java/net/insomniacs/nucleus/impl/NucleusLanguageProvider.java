package net.insomniacs.nucleus.impl;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.insomniacs.nucleus.api.NucleusDataGenerator;
import net.insomniacs.nucleus.api.annotations.Translate;
import net.insomniacs.nucleus.impl.utility.ProviderUtils;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

import static net.insomniacs.nucleus.impl.utility.AnnotationUtils.getAnnotation;
import static net.insomniacs.nucleus.impl.utility.ProviderUtils.streamAllRegistries;
import static net.minecraft.util.Util.createTranslationKey;

public class NucleusLanguageProvider extends FabricLanguageProvider {

	private final NucleusDataGenerator generator;

	public NucleusLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup, NucleusDataGenerator generator) {
		super(dataOutput, registryLookup);

		this.generator = generator;
	}

	@Override
	public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder builder) {
		streamAllRegistries(generator, (registry, entry) -> {
			var value = entry.value();
			var id = new Identifier(entry.getIdAsString());
			var parsedID = ProviderUtils.parseID(id);

			var entryClazz = value.getClass();
			var annotation = getAnnotation(entryClazz, Translate.class);

			var translationKey = createTranslationKey(registry.getKey().getRegistry().getPath(), id);

			if (annotation != null)
				builder.add(translationKey, annotation.name());
			else
				builder.add(translationKey, parsedID);
		});
	}
}
