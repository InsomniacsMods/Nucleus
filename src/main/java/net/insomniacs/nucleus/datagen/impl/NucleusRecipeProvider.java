package net.insomniacs.nucleus.datagen.impl;

import net.insomniacs.nucleus.datagen.api.NucleusDataGenerator;
import net.insomniacs.nucleus.datagen.impl.annotations.ShapedRecipes;
import net.insomniacs.nucleus.datagen.impl.annotations.ShapelessRecipes;
import net.insomniacs.nucleus.datagen.impl.utility.ProviderUtils;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

import static net.insomniacs.nucleus.datagen.impl.utility.AnnotationUtils.getAnnotation;

public class NucleusRecipeProvider extends RecipeProvider {

    private final NucleusDataGenerator dataGenerator;

    public NucleusRecipeProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture, NucleusDataGenerator generator) {
        super(output, registryLookupFuture);

        this.dataGenerator = generator;
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ProviderUtils.streamRegistry(Registries.ITEM, dataGenerator, item -> {
            var value = item.value();
            var shapedRecipes = getAnnotation(value, ShapedRecipes.class);
            var shapelessRecipes = getAnnotation(value, ShapelessRecipes.class);
            var id = new Identifier(item.getIdAsString());

            for (var i = 0; i < shapedRecipes.value().length; i++) {
                var shapedRecipe = shapedRecipes.value()[i];
                var builder = ShapedRecipeJsonBuilder
                        .create(shapedRecipe.category(), value);
                var patterns = shapedRecipe.pattern().split("\n");

                for (var pattern : patterns) builder.pattern(pattern);
                for (var pair : getPairs(shapedRecipe.inputs())) builder.input(pair.input(), pair.item());

                if (shapedRecipes.value().length > 1)
                    builder.offerTo(exporter, id.withSuffixedPath("_" + i));
                else
                    builder.offerTo(exporter);
            }

            for (int i = 0; i < shapelessRecipes.value().length; i++) {
                var shapelessRecipe = shapelessRecipes.value()[i];
                var builder = ShapelessRecipeJsonBuilder
                        .create(shapelessRecipe.category(), value);

                for (var itemId : shapelessRecipe.input().split(",")) {
                    var itemInput = Registries.ITEM.get(new Identifier(itemId));
                    builder.input(itemInput);
                }

                if (shapedRecipes.value().length > 1)
                    id.withPrefixedPath("shapeless_");

                if (shapelessRecipes.value().length > 1)
                    builder.offerTo(exporter, id.withSuffixedPath("_" + i));
                else
                    builder.offerTo(exporter);
            }

        });
    }

    InputItemPair[] getPairs(String inputString) {
        var chunks = inputString.split(",");
        var pairs = new InputItemPair[chunks.length];

        for (var i = 0; i < chunks.length; i++) {
            var splitChunk = chunks[i].split("=");
            var input = splitChunk[0].charAt(0);
            var itemID = new Identifier(splitChunk[1]);
            var itemVariant = Registries.ITEM.get(itemID);

            pairs[i] = new InputItemPair(input, itemVariant);
        }

        return pairs;
    }


    record InputItemPair(char input, Item item) {}
}
