package net.insomniacs.nucleus.impl.mixins.common;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.rmi.registry.Registry;
import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class ServerMixin {

    /**
     * The central mixin for ticking components.
     * @param booleanSupplier literally no clue, its passed along to be used a grand total of once.
     * @param ci callback info for cancelling event
     */
    @Inject(method = "tick", at = @At("HEAD"))
    void Nucleus$tickInject(BooleanSupplier booleanSupplier, CallbackInfo ci) {
        // TODO: 9/29/2023 Re-Integrate tick-wise logic
    }

}
