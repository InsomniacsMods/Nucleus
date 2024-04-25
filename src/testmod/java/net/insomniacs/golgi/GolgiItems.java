package net.insomniacs.golgi;

import net.insomniacs.nucleus.api.components.NucleusComponents;
import net.insomniacs.nucleus.api.components.custom.FontChangingComponent;
import net.insomniacs.nucleus.api.items.LocationBindingItem;
import net.insomniacs.nucleus.api.items.SignFontChangingItem;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import static net.insomniacs.golgi.Golgi.REGISTRY;

public class GolgiItems {

    public static void init() {}

    public static final Item REDSTONE_TRACKER = REGISTRY.item(
            "redstone_tracker",
            new LocationBindingItem(new Item.Settings())
    );

    public static final Item ILLAGER_RUNE = REGISTRY.item(
            "illager_rune",
            new SignFontChangingItem(
                    new Item.Settings()
                            .component(NucleusComponents.FONT_CHANGING, new FontChangingComponent(new Identifier("minecraft", "illageralt"), true)),
                    SoundEvents.ITEM_DYE_USE
            )
    );

}
