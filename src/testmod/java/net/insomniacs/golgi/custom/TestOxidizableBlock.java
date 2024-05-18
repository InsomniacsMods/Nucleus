package net.insomniacs.golgi.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.registry.Registries;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestOxidizableBlock extends Block {

	BooleanProperty WAXED = BooleanProperty.of("waxed");
	OxidationProperty OXIDATION = OxidationProperty.of("oxidation");

	public TestOxidizableBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(WAXED, false).with(OXIDATION, Oxidizable.OxidationLevel.UNAFFECTED));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(WAXED, OXIDATION);
	}

	@Override
	protected List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
		List<ItemStack> results = super.getDroppedStacks(state, builder);
		results.forEach(stack -> stack.set(DataComponentTypes.BLOCK_STATE, asComponent(state)));
		return results;
	}

	public BlockStateComponent asComponent(BlockState state) {
		return BlockStateComponent.DEFAULT
				.with(WAXED, state.get(WAXED))
				.with(OXIDATION, state.get(OXIDATION));
	}

}
