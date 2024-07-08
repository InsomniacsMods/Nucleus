package net.insomniacs.nucleus.datagen.impl;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.insomniacs.nucleus.datagen.api.Datagen;
import net.insomniacs.nucleus.datagen.impl.annotations.ShapedRecipes;
import net.insomniacs.nucleus.datagen.impl.annotations.ShapelessRecipes;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static net.insomniacs.nucleus.datagen.impl.utility.AnnotationUtils.getAnnotation;

public class NucleusRecipeProvider extends RecipeProvider {

    private final Map<Registry<?>, Datagen.RefAnnotationPair[]> refMap;

    public NucleusRecipeProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture, Map<Registry<?>, Datagen.RefAnnotationPair[]> refMap) {
        super(output, registryLookupFuture);
        this.refMap = refMap;
    }

    @Override
    public void generate(RecipeExporter exporter) {
        Arrays.stream(refMap.get(Registries.ITEM)).forEach(pair -> {
            var ref = pair.reference();
            var item = (Item) ref.value();

            var shapedRecipes = getAnnotation(item.getClass(), ShapedRecipes.class);
            var shapelessRecipes = getAnnotation(item.getClass(), ShapelessRecipes.class);
            final var id = Identifier.of(ref.getIdAsString());

            shapedRecipes.ifPresent(recipes -> {
                for (var i = 0; i < recipes.value().length; i++) {
                    var shapedID = id;

                    var shapedRecipe = recipes.value()[i];
                    var builder = ShapedRecipeJsonBuilder
                            .create(shapedRecipe.category(), item);
                    var patterns = shapedRecipe.pattern().split("\n");

                    for (var pattern : patterns) builder.pattern(pattern);
                    for (var patternPair : getPairs(shapedRecipe.inputs())) {
                        builder.input(patternPair.input(), patternPair.item());
                        builder.criterion(
                                FabricRecipeProvider.hasItem(patternPair.item),
                                FabricRecipeProvider.conditionsFromItem(patternPair.item)
                        );
                    }

                    shapedID.withPrefixedPath("shaped/");

                    if (recipes.value().length > 1)
                        builder.offerTo(exporter, shapedID.withSuffixedPath("_" + i));
                    else
                        builder.offerTo(exporter);
                }
            });

            shapelessRecipes.ifPresent( shapeless -> {
                for (int i = 0; i < shapeless.value().length; i++) {
                    var shapelessID = id;

                    var shapelessRecipe = shapeless.value()[i];
                    var builder = ShapelessRecipeJsonBuilder
                            .create(shapelessRecipe.category(), item);

                    for (var itemId : shapelessRecipe.input().split(",")) {
                        var itemInput = Registries.ITEM.get(Identifier.of(itemId));

                        builder.input(itemInput);
                        builder.criterion(
                                FabricRecipeProvider.hasItem(itemInput),
                                FabricRecipeProvider.conditionsFromItem(itemInput)
                        );
                    }

                    shapelessID.withPrefixedPath("shapeless/");

                    if (shapeless.value().length > 1)
                        builder.offerTo(exporter, shapelessID.withSuffixedPath("_" + i));
                    else
                        builder.offerTo(exporter);
                }
            });
        });
    }

    InputItemPair[] getPairs(String inputString) {
        var chunks = inputString.split(",");
        var pairs = new InputItemPair[chunks.length];

        for (var i = 0; i < chunks.length; i++) {
            var splitChunk = chunks[i].split("=");
            var input = splitChunk[0].charAt(0);
            var itemID = Identifier.of(splitChunk[1]);
            var itemVariant = Registries.ITEM.get(itemID);

            pairs[i] = new InputItemPair(input, itemVariant);
        }

        return pairs;
    }


    record InputItemPair(char input, Item item) {}
}
