package net.insomniacs.nucleus.api.content;

import net.insomniacs.nucleus.Nucleus;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class NucleusTags {

    public static final TagKey<Item> SOULBOUND_ITEMS = TagKey.of(RegistryKeys.ITEM, Nucleus.of("soulbound_items"));
    public static final TagKey<Item> CANNOT_BE_INSERTED_INTO_BUNDLES = TagKey.of(RegistryKeys.ITEM, Nucleus.of("cannot_be_inserted_into_bundles"));

}
