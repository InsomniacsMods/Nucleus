package net.insomniacs.nucleus.impl.modreg.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.insomniacs.nucleus.api.modreg.ModEntry;
import net.insomniacs.nucleus.api.modreg.ModRegistry;

public class NucleusLangProvider extends FabricLanguageProvider {

    public NucleusLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder generator) {
        ModRegistry.getEntries().forEach(entry -> processEntry(generator, entry));
    }

    private void processEntry(TranslationBuilder generator, ModEntry<?,?,?,?> entry) {
        if (!entry.translate) return;
        String id = entry.getId().toTranslationKey(entry.getType().getPath());
        generator.add(id, entry.translatedName);
    }

}
