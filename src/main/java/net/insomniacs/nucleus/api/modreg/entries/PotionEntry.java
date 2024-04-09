package net.insomniacs.nucleus.api.modreg.entries;

import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.ModEntry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class PotionEntry extends ModEntry<PotionEntry, PotionEntry.Builder, Potion> {

    @Override
    public Registry<Potion> getRegistry() {
        return Registries.POTION;
    }

    @Override
    protected Potion generateValue() {
        return new Potion();
    }

    public PotionEntry(Builder settings) {
        super(settings);
    }

    public static class Builder extends ModEntry.EntryBuilder<Builder, PotionEntry, Potion> {

        public Builder(Identifier id) {
            super(id);
        }

        @Override
        protected PotionEntry createEntry() {
            return new PotionEntry(this);
        }

    }

}
