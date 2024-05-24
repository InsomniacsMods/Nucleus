package net.insomniacs.nucleus.datagen.impl;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.insomniacs.nucleus.datagen.api.NucleusDataGenerator;
import net.insomniacs.nucleus.datagen.api.annotations.Translate;
import net.insomniacs.nucleus.datagen.impl.utility.ProviderUtils;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.concurrent.CompletableFuture;

import static net.insomniacs.nucleus.datagen.impl.utility.AnnotationUtils.getAnnotation;

public class NucleusLanguageProvider extends FabricLanguageProvider {

	private final NucleusDataGenerator generator;

	public NucleusLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup, NucleusDataGenerator generator) {
		super(dataOutput, registryLookup);

		this.generator = generator;
	}

	@Override
	public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder builder) {
		ProviderUtils.streamAllRegistries(generator, (registry, entry) -> generateTranslation(builder, registry, entry));
	}

	public static void generateTranslation(TranslationBuilder builder, Registry<?> registry, RegistryEntry.Reference<?> entry) {
		var value = entry.value();
		var id = new Identifier(entry.getIdAsString());

		var entryClazz = value.getClass();
		var annotation = getAnnotation(entryClazz, Translate.class);

		String translationKey = Util.createTranslationKey(registry.getKey().getValue().getPath(), id);
		String translation;
		System.out.println(annotation);
		if (annotation != null) translation = annotation.name();
		else translation = ProviderUtils.parseID(id);

		builder.add(translationKey, translation);
	}

}
