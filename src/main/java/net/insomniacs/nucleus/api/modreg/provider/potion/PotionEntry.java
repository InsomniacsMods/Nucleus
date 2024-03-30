package net.insomniacs.nucleus.api.modreg.provider.potion;

import net.insomniacs.nucleus.api.modreg.provider.Entry;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;

public class PotionEntry extends Entry<Potion, PotionBuilder> {

    public PotionEntry(Identifier id, Potion value, PotionBuilder settings) {
        super(id, value, settings);
    }

}
