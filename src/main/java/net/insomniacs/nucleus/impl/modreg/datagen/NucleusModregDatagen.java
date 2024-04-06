package net.insomniacs.nucleus.impl.modreg.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.insomniacs.nucleus.impl.modreg.datagen.providers.NucleusLangProvider;
import net.insomniacs.nucleus.impl.modreg.datagen.providers.NucleusLootTableProvider;
import net.insomniacs.nucleus.impl.modreg.datagen.providers.NucleusModelProvider;

public class NucleusModregDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(NucleusModelProvider::new);
        pack.addProvider(NucleusLangProvider::new);
        pack.addProvider(NucleusLootTableProvider::new);
    }

}
