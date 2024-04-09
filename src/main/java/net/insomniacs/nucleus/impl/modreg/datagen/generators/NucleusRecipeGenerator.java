package net.insomniacs.nucleus.impl.modreg.datagen.generators;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.insomniacs.nucleus.api.modreg.entries.ItemEntry;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.util.Identifier;

public class NucleusRecipeGenerator extends FabricRecipeProvider {

	public NucleusRecipeGenerator(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generate(RecipeExporter generator) {
		ModRegistry.<ItemEntry>getEntries("item")
				.forEach(entry -> processItem(generator, entry));
	}

	private void processItem(RecipeExporter generator, ItemEntry entry) {
		entry.getRecipes().forEach(recipe -> recipe.offerTo(generator));
	}

}
