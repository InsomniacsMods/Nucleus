package net.insomniacs.nucleus.api.content;

import net.insomniacs.nucleus.Nucleus;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class NucleusTags {

    public static final TagKey<Item> SOULBOUND_ITEMS = item("soulbound_items");
    public static final TagKey<Item> CANNOT_BE_INSERTED_INTO_BUNDLES = item("cannot_be_inserted_into_bundles");

    public static final TagKey<EntityType<?>> CAN_WALK_THROUGH_LEAVES = entity("can_walk_through_leaves");

    public static TagKey<Item> item(String path) {
        return TagKey.of(RegistryKeys.ITEM, Nucleus.of(path));
    }

    public static TagKey<EntityType<?>> entity(String path) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Nucleus.of(path));
    }

}
