package net.insomniacs.nucleus.datagen.impl;

import net.insomniacs.nucleus.datagen.api.annotations.ItemModel;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;

import static net.insomniacs.nucleus.datagen.impl.utility.AnnotationUtils.getAnnotation;

public class NucleusItemModelProvider {

	public static void generateItemModel(ItemModelGenerator generator, Registry<?> registry, RegistryEntry.Reference<?> entry) {
		if (!registry.equals(Registries.ITEM)) return;
		var value = (Item) entry.value(); // We know it at minimum extends item.
		var itemClazz = value.getClass();

		var modelAnnotation = getAnnotation(itemClazz, ItemModel.class);

		Model result = null;
		if (modelAnnotation != null) result = modelFromAnnotation(modelAnnotation);
		if (result == null) result = guessModel(value);

		generator.register(value, result);
	}

	public static Model modelFromAnnotation(ItemModel annotation) {
		if (annotation.cancel()) return null;
		try {
			var modelsClazz = annotation.modelHome();
			var modelField = modelsClazz.getField(annotation.model());

			modelField.setAccessible(true);
			return (Model)modelField.get(null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
//			LOGGER.info("Model: " + annotation.model() + " doesn't exist.");
			// TODO why the fuck is the logger not accessible here
			return null;
		}
	}

	public static Model guessModel(Item value) {
		var model = Models.HANDHELD;

		if (value instanceof BlockItem)
			model = Models.GENERATED;

		if (isToolItem(value))
			model = Models.HANDHELD;

		return model;
	}

	public static boolean isToolItem(Item item) {
		var stack = item.getDefaultStack();
		return stack.isIn(ItemTags.AXES) ||
				stack.isIn(ItemTags.PICKAXES) ||
				stack.isIn(ItemTags.SWORDS) ||
				stack.isIn(ItemTags.HOES) ||
				stack.isIn(ItemTags.SHOVELS);
	}

}
