package net.insomniacs.nucleus.api.modreg.entries;

import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.ModEntry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class PotionEntry extends ModEntry<PotionEntry, PotionEntry.Builder, StatusEffectInstance, Potion> {

    public PotionEntry(Builder settings) {
        super(settings);
    }

    @Override
    public Identifier getType() {
        return Nucleus.id("potion");
    }

    public static class Builder extends EntryBuilder<Builder, PotionEntry, StatusEffectInstance, Potion> {

        public Builder(Identifier id, Function<StatusEffectInstance, Potion> constructor) {
            super(id, constructor, new StatusEffectInstance(StatusEffects.POISON), Registries.POTION);
        }

        @Override
        protected PotionEntry createEntry() {
            return new PotionEntry(this);
        }

    }

}
