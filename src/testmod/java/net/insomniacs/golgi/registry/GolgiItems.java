package net.insomniacs.golgi.registry;

import net.insomniacs.golgi.content.item.BreadstickBasketItem;
import net.insomniacs.nucleus.api.components.FontChangingComponent;
import net.insomniacs.nucleus.api.items.LocationBindingItem;
import net.insomniacs.nucleus.api.items.SignFontChangingItem;
import net.insomniacs.nucleus.datagen.api.annotations.DatagenExempt;
import net.insomniacs.nucleus.datagen.api.annotations.ShapelessCrafting;
import net.insomniacs.nucleus.datagen.api.annotations.Translate;
import net.insomniacs.nucleus.impl.components.NucleusComponents;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import static net.insomniacs.golgi.Golgi.REGISTRY;

@SuppressWarnings("unused")
public class GolgiItems {

    public static void init() {}

    @Translate(name="AAAAAAAA")
    @ShapelessCrafting(input= "stone, redstone", keyOverride = "foo")
    @ShapelessCrafting(input= "stone, cobblestone", keyOverride = "foo")
    public static final Item REDSTONE_TRACKER = REGISTRY.item("redstone_tracker",
            LocationBindingItem::new
    );


    private static final FontChangingComponent ILLAGER_RUNE_FONT = new FontChangingComponent(
            Identifier.of("minecraft", "illageralt"),
            true
    );

    @ShapelessCrafting(input = "stone, bowl", keyOverride = "foo")
    @DatagenExempt(DatagenExempt.Exemption.TRANSLATE)
    public static final Item ILLAGER_RUNE = REGISTRY.item("illager_rune", settings ->
            new SignFontChangingItem(
                    settings.component(NucleusComponents.FONT_CHANGING, ILLAGER_RUNE_FONT),
                    SoundEvents.ITEM_DYE_USE
            )
    );

    @Translate(name = "OLIVE GARDEN TAKEOUT")
    public static final Item BREADSTICK_BASKET = REGISTRY.item("breadstick_basket", settings ->
            new BreadstickBasketItem(
                    settings.unstackable(),
                    127
            )
    );

}
