package net.insomniacs.nucleus.impl.mixins.common;

import net.insomniacs.nucleus.api.components.DataComponent;
import net.insomniacs.nucleus.impl.components.ComponentContainer;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;
import java.util.Objects;

@Mixin(value = {Entity.class, MinecraftServer.class})
public abstract class DataComponentAttachment implements ComponentContainer {

    List<DataComponent> components;

    @Override
    public void addComponent(DataComponent dataComponent) {
        Objects.requireNonNull(dataComponent, "dataComponent cannot be null.");
        components.add(dataComponent);
    }

    @Override
    public List<DataComponent> getComponents() {
        return components;
    }
}
