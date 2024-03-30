package net.insomniacs.nucleus.api.modreg.provider.block;

import net.insomniacs.nucleus.api.modreg.provider.Entry;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class BlockEntry extends Entry<Block, BlockBuilder> {

    public BlockEntry(Identifier id, Block value, BlockBuilder settings) {
        super(id, value, settings);
    }

}
