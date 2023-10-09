package net.insomniacs.nucleus.impl.mixins.common;

import net.insomniacs.nucleus.api.components.DataComponent;
import net.insomniacs.nucleus.impl.components.ComponentContainer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(Entity.class)
public abstract class EntityMixin {

    /**
     * A map to hold namespaced NBT in a bunch while its eventually re-allocated.
     */
    private Map<String, NbtCompound> namespacedNBT = new HashMap<>();

    @Shadow public void populateCrashReport(CrashReportSection category) {}

    @Inject(method = "writeNbt", at = @At("HEAD"))
    private void nucleus$readNBTInject(NbtCompound compoundTag, CallbackInfoReturnable<NbtCompound> cir) {
        try {
            // Prep components by writing them to normal NBT first.
            getContainer().getComponents().forEach(component -> HandleComponent(component, compoundTag));
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create(throwable, "Saving Nucleus DataComponents to entity NBT.");
            CrashReportSection crashReportCategory = crashReport.addElement("Entity being saved");
            this.populateCrashReport(crashReportCategory);
            throw new CrashException(crashReport);
        }
    }

    /**
     * Returns a container of this entity. Mainly just a helper method.
     * @return a component container of type Entity
     */
    @Unique
    private ComponentContainer getContainer() {
        return (ComponentContainer) this;
    }


    /**
     * Handles general component stuff, this method mainly exists to look pretty.
     * @param component component currently being handled.
     * @param tag reference of tag to be modified
     */
    @Unique
    private void HandleComponent(DataComponent component, NbtCompound tag) {
        // TODO: 9/29/2023 Come back to this post mappings transfer
        // component.writeToNBT(tag);
    }

}
