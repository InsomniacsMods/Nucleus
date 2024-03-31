package net.insomniacs.nucleus.api.modreg;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public record ItemGroupEntry (
        RegistryKey<ItemGroup> group,
        @Nullable ItemStack after
) {}
