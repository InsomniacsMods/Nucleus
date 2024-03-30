package net.insomniacs.nucleus.api.modreg.provider.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public record ItemGroupEntry (
        RegistryKey<ItemGroup> tab,
        Identifier id,
        @Nullable ItemStack after
) {}
