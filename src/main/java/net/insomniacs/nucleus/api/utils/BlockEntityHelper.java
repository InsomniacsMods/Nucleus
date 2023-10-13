package net.insomniacs.nucleus.api.utils;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;

public interface BlockEntityHelper {

    static <T extends BlockEntityType<? extends BlockEntity>> BlockEntityHelper of(T blockEntity) {
        return (BlockEntityHelper) blockEntity;
    }

    void addBlocks(Block... blocks);
}
