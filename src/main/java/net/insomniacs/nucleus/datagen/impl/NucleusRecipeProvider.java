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
            final var ref = pair.reference();
            final var item = (Item) ref.value();

            final var shapedRecipes = getAnnotation(pair.annotations(), ShapedRecipes.class)
                    .or(() -> getAnnotation(pair.annotations(), ShapedRecipes.class));
            final var shapelessRecipes = getAnnotation(pair.annotations(), ShapelessRecipes.class)
                    .or(() -> getAnnotation(pair.annotations(), ShapelessRecipes.class));
            final var id = Identifier.of(ref.getIdAsString());
            final var key = id.toUnderscoreSeparatedString() + "_key";

            // My dumbass decided to use lambdas for optionals, so here we are with a cheat
            int[] recipeCount = new int[] { 0 };

            shapedRecipes.ifPresent(recipes -> {
                for (var i = 0; i < recipes.value().length; i++) {
                    var shapedID = id;

                    var shapedRecipe = recipes.value()[i];
                    var builder = ShapedRecipeJsonBuilder
                            .create(shapedRecipe.category(), item)
                            .group(shapedRecipe.keyOverride().isEmpty() ? key : shapedRecipe.keyOverride());
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

                    recipeCount[0] = i;
                }
            });

            shapelessRecipes.ifPresent( shapeless -> {
                for (int i = 0; i < shapeless.value().length; i++) {
                    var shapelessID = id;

                    var shapelessRecipe = shapeless.value()[i];
                    var builder = ShapelessRecipeJsonBuilder
                            .create(shapelessRecipe.category(), item)
                            .group(shapelessRecipe.keyOverride().isEmpty() ? key : shapelessRecipe.keyOverride());

                    for (var itemId : shapelessRecipe.input().split(",")) {
                        var itemInput = Registries.ITEM.get(Identifier.of(itemId.trim().toLowerCase()));

                        builder.input(itemInput);
                        builder.criterion(
                                FabricRecipeProvider.hasItem(itemInput),
                                FabricRecipeProvider.conditionsFromItem(itemInput)
                        );
                    }

                    shapelessID.withPrefixedPath("shapeless/");

                    if (shapeless.value().length > 1)
                        builder.offerTo(exporter, shapelessID.withSuffixedPath("_" + (recipeCount[0] + i)));
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
