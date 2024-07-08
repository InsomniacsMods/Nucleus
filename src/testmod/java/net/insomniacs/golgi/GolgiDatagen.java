package net.insomniacs.golgi;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.insomniacs.golgi.registry.GolgiBlocks;
import net.insomniacs.golgi.registry.GolgiItems;
import net.insomniacs.nucleus.datagen.api.Datagen;

public class GolgiDatagen implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		Datagen.generateData("golgi", generator, GolgiItems.class, GolgiBlocks.class);
	}

}
