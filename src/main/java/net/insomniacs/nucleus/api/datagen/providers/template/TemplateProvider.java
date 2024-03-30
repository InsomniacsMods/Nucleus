package net.insomniacs.nucleus.api.datagen.providers.template;

import net.insomniacs.nucleus.api.datagen.providers.BaseModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TemplateProvider<G> {

    public final TexturedModel.Factory[] models;
    public final List<BaseModelProvider.BaseModelConsumer<G>> consumers;

    public TemplateProvider(List<BaseModelProvider.BaseModelConsumer<G>> consumers, TexturedModel.Factory... models) {
        this.consumers = consumers;
        this.models = models;
    }

    public <O> void addAll(O[] objects, TriConsumer<G, O, TexturedModel.Factory> consumer) {
        this.consumers.add(generator -> {
            for (O object : objects) {
                for (TexturedModel.Factory model : models) {
                    consumer.accept(generator, object, model);
                }
            }
        });
    }


    public static TexturedModel.Factory texturedModel(Function<Block, TextureMap> getter, Identifier id, TextureKey... keys) {
        Model model = new Model(Optional.of(id), Optional.empty(), keys);
        return TexturedModel.makeFactory(getter, model);
    }

}
