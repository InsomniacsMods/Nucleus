package net.insomniacs.nucleus.asm;

import net.minecraft.item.Item;

@SuppressWarnings("unused")
public interface NucleusItemSettings {

	default Item.Settings unstackable() {
		var self = (Item.Settings)this;
		return self.maxCount(1);
	}

}
