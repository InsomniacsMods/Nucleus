package net.insomniacs.nucleus.impl.modreg.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.insomniacs.nucleus.impl.modreg.datagen.generators.NucleusLangGenerator;
import net.insomniacs.nucleus.impl.modreg.datagen.generators.NucleusLootTableGenerator;
import net.insomniacs.nucleus.impl.modreg.datagen.generators.NucleusModelGenerator;
import net.insomniacs.nucleus.impl.modreg.datagen.generators.NucleusRecipeGenerator;
import net.insomniacs.nucleus.impl.modreg.datagen.generators.tag.NucleusBlockTagGenerator;
import net.insomniacs.nucleus.impl.modreg.datagen.generators.tag.NucleusItemTagGenerator;

public class NucleusModregDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(NucleusModelGenerator::new);
        pack.addProvider(NucleusLangGenerator::new);
        pack.addProvider(NucleusLootTableGenerator::new);
        pack.addProvider(NucleusRecipeGenerator::new);

        // TODO maybe replace with a generic generator? idk how to right now
        pack.addProvider(NucleusItemTagGenerator::new);
        pack.addProvider(NucleusBlockTagGenerator::new);
    }

}
