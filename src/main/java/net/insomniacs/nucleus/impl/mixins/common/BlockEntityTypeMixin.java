package net.insomniacs.nucleus.impl.mixins.common;

import com.google.common.collect.ImmutableSet;
import net.insomniacs.nucleus.api.utils.BlockEntityHelper;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Mixin(BlockEntityType.class)
public abstract class BlockEntityTypeMixin implements BlockEntityHelper {

    @Mutable @Shadow @Final private Set<Block> blocks;

    @Override
    public void addBlocks(Block... blocks) {
        // Make a temporary list, include all past entries and then introduce our own then finalize it as a set.
        if(this.blocks instanceof ImmutableSet<Block>) {
            List<Block> tempList = new ArrayList<>(List.of(blocks));
            tempList.addAll(this.blocks);
            this.blocks = Set.copyOf(tempList);
        }
    }
}
