package net.insomniacs.nucleus.asm;

import net.insomniacs.nucleus.api.components.InfoTooltipComponent;
import net.insomniacs.nucleus.impl.components.NucleusComponents;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

@SuppressWarnings("unused")
public interface NucleusItemSettings {

	default Item.Settings unstackable() {
		var self = (Item.Settings)this;
		return self.maxCount(1);
	}

	default Item.Settings info(Text... tooltips) {
		var self = (Item.Settings)this;
		return self.component(NucleusComponents.INFO, new InfoTooltipComponent(tooltips));
	}

}
