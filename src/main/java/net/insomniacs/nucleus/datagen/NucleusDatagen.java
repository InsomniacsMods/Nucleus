package net.insomniacs.nucleus.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.insomniacs.nucleus.datagen.providers.lang.NucleusLangProvider;
import net.insomniacs.nucleus.datagen.providers.model.NucleusModelProvider;
import net.insomniacs.nucleus.datagen.providers.tag.NucleusTagProvider;

public class NucleusDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(NucleusModelProvider::new);
        pack.addProvider(NucleusLangProvider::new);
        pack.addProvider(NucleusTagProvider::new);
    }

}
