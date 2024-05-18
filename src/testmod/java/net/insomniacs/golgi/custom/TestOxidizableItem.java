package net.insomniacs.golgi.custom;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class TestOxidizableItem extends BlockItem {

	public TestOxidizableItem(TestOxidizableBlock block, Settings settings) {
		super(block, settings);
	}

	@Override
	public String getTranslationKey() {
		String key = super.getTranslationKey();
		return this.getBlock().getTranslationKey();
	}

}
