package net.insomniacs.nucleus;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.insomniacs.nucleus.api.NucleusDataGenerator;
import net.insomniacs.nucleus.impl.NucleusLanguageProvider;

public class NucleusDatagen implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		new NucleusDataGenerator("golgi", generator).generate();
	}

}
