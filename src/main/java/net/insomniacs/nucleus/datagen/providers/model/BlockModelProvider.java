package net.insomniacs.nucleus.datagen.providers.model;

import net.insomniacs.nucleus.datagen.providers.template.TemplateProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.data.family.BlockFamily;

import java.util.List;

public class BlockModelProvider extends BaseModelProvider<BlockStateModelGenerator> {

    public void addFamily(BlockFamily... families) {
        addAll(families, (generator, family) -> generator.registerCubeAllModelTexturePool(family.getBaseBlock()).family(family));
    }

    public void add(Block... blocks) {
        addAll(blocks, BlockStateModelGenerator::registerSimpleCubeAll);
    }

    public void addPillar(Block... blocks) {
        addAll(blocks, (generator, block) -> generator.registerAxisRotated(block, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL));
    }

    public static class BlockTemplateProvider extends TemplateProvider<BlockStateModelGenerator> {

        public BlockTemplateProvider(List<BaseModelConsumer<BlockStateModelGenerator>> baseModelConsumers, TexturedModel.Factory... models) {
            super(baseModelConsumers, models);
        }

        public void addBlock(Block... blocks) {
//            this.consumers.add(consumer -> {
//                for (Block block : blocks) {
//                    for (TexturedModel.Factory model : models) {
//                        consumer.registerSingleton(block, model);
//                        consumer.registerParentedItemModel(new BlockItem(block, new Item.Settings()), identifiers[0]);
//                    }
//                }
//            });
        }

    }

    public BlockTemplateProvider fromTemplate(TexturedModel.Factory... models) {
        return new BlockTemplateProvider(this.consumers, models);
    }

}