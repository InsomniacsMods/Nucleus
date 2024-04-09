package net.insomniacs.nucleus.impl.modreg.datagen.generators;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.insomniacs.nucleus.api.modreg.ModEntry;
import net.insomniacs.nucleus.api.modreg.ModRegistry;

public class NucleusLangGenerator extends FabricLanguageProvider {

    public NucleusLangGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder generator) {
        ModRegistry.getEntries()
                .stream()
                .filter(ModEntry::isTranslated)
                .forEach(entry -> processEntry(generator, entry));
    }

    private void processEntry(TranslationBuilder generator, ModEntry<?,?,?> entry) {
        String id = entry.id().toTranslationKey(entry.getType().getPath());
        generator.add(id, entry.getTranslatedName());
    }

}
