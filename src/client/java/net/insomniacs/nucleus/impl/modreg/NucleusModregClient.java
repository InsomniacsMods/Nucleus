package net.insomniacs.nucleus.impl.modreg;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.modreg.ModRegistry;
import net.insomniacs.nucleus.api.modreg.entries.ItemEntry;
import net.minecraft.item.ItemStack;

public class NucleusModregClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModregCreativeTabs.process();
        ModregRenderLayers.process();
    }

}
