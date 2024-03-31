package net.insomniacs.nucleus.datagen.providers.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.insomniacs.nucleus.api.modreg.DefaultedModEntry;
import net.insomniacs.nucleus.api.modreg.ModRegistry;

public class NucleusLangProvider extends FabricLanguageProvider {

    public NucleusLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder generator) {
        ModRegistry.ENTRIES.forEach(entry -> processEntry(generator, entry));
    }

    private void processEntry(TranslationBuilder generator, DefaultedModEntry<?, ?> entry) {
        if (!entry.translate) return;
        String id = entry.id().toTranslationKey(entry.getType().getPath());
        generator.add(id, entry.translatedName);
    }

}
