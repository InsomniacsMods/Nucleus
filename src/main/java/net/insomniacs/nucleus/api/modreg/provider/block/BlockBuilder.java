package net.insomniacs.nucleus.api.modreg.provider.block;

import net.insomniacs.nucleus.api.modreg.provider.EntryBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

public class BlockBuilder extends EntryBuilder<BlockEntry, Block.Settings, Block> {

    public BlockBuilder(Identifier id, Function<Block.Settings, Block> constructor) {
        super(id, constructor, AbstractBlock.Settings.create(), Registries.BLOCK);
    }

    @Override
    public BlockEntry createEntry() {
        return new BlockEntry(id, construct(), this);
    }

    @Override
    public BlockBuilder modifySettings(UnaryOperator<Block.Settings> modifier) {
        return (BlockBuilder)super.modifySettings(modifier);
    }


    public BlockBuilder luminance(ToIntFunction<BlockState> valueFunction) {
        return modifySettings(settings -> settings.luminance(valueFunction));
    }

    public BlockBuilder luminance(int value) {
        return luminance(n -> value);
    }

}
