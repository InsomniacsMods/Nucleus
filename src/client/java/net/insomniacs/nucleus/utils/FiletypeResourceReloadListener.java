package net.insomniacs.nucleus.utils;

import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.Map;

public abstract class FiletypeResourceReloadListener extends EarlyResourceReloadListener {

    public abstract String getDirectory();
    public abstract String getFileExtension();

    public abstract void processFiles(Map<Identifier, Resource> files);

    @Override
    public void reload(ResourceManager manager) {
        Map<Identifier, Resource> files = manager.findResources(this.getDirectory(), r -> r.getPath().endsWith(this.getFileExtension()));
        processFiles(files);
    }

}
