package net.insomniacs.nucleus.datagen.impl;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.insomniacs.nucleus.datagen.api.Datagen;
import net.insomniacs.nucleus.datagen.api.annotations.Translate;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static net.insomniacs.nucleus.datagen.impl.utility.AnnotationUtils.getAnnotation;

public class NucleusLanguageProvider extends FabricLanguageProvider {

	private final Map<Registry<?>, Datagen.RefAnnotationPair[]> refMap;

	public NucleusLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup, Map<Registry<?>, Datagen.RefAnnotationPair[]> refMap) {
		super(dataOutput, registryLookup);
		this.refMap = refMap;
	}

	@Override
	public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder builder) {
		refMap.forEach((registry, refPairs) -> {
			for (var pair : refPairs) {
				var ref = pair.reference();
				var value = ref.value();
				var id = Identifier.of(ref.getIdAsString());

				// probably overuse of optional, was tired, do not care.
				var fieldAnnotation = getAnnotation(pair.annotations(), Translate.class).map(Translate::name);
				var clazzAnnotation = getAnnotation(value.getClass(), Translate.class).map(Translate::name);

				var translationKey = Util.createTranslationKey(registry.getKey().getValue().getPath(), id);
				var translation = fieldAnnotation.orElse(clazzAnnotation.orElse(parseID(id)));

				builder.add(translationKey, translation);
			}
        });
	}

	static <T> String parseID(Identifier id) {
		return Arrays
				.stream(id.getPath().split("([_.])")) // match either _ or .
				.map(NucleusLanguageProvider::makeUpperCase)
				.collect(Collectors.joining(" "));
	}

	static String makeUpperCase(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}


}
