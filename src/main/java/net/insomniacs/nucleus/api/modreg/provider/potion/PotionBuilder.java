package net.insomniacs.nucleus.api.modreg.provider.potion;

import net.insomniacs.nucleus.api.modreg.provider.EntryBuilder;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class PotionBuilder extends EntryBuilder<PotionEntry, StatusEffectInstance, Potion> {

    public PotionBuilder(Identifier id, Function<StatusEffectInstance, Potion> constructor) {
        super(id, constructor, new StatusEffectInstance(StatusEffects.POISON), Registries.POTION);
    }

    @Override
    public PotionEntry createEntry() {
        return new PotionEntry(id, construct(), this);
    }

}
