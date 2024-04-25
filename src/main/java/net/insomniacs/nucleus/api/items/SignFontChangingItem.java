package net.insomniacs.nucleus.api.items;

import net.insomniacs.nucleus.api.components.NucleusComponents;
import net.insomniacs.nucleus.api.components.custom.FontChangingComponent;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.SignText;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignChangingItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class SignFontChangingItem extends Item implements SignChangingItem {

    private final SoundEvent sound;

    public SignFontChangingItem(Item.Settings settings, SoundEvent sound) {
        super(settings);
        this.sound = sound;
    }

    public static FontChangingComponent getComponent(ItemStack stack) {
        return stack.getOrDefault(NucleusComponents.FONT_CHANGING, FontChangingComponent.EMPTY);
    }

    public static Identifier getFont(ItemStack stack) {
        return getComponent(stack).font();
    }

    public static boolean hasFont(ItemStack stack) {
        Identifier font = getFont(stack);
        return !font.toString().isEmpty();
    }

    @Override
    public boolean useOnSign(World world, SignBlockEntity signBlock, boolean front, PlayerEntity player) {
        ItemStack stack = player.getActiveItem();
        if (!hasFont(stack)) return false;

        if (signBlock.changeText(text -> changeFont(text, getFont(stack)), front)) {
            world.playSound(null, signBlock.getPos(), this.sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
            return true;
        }
        return false;
    }

    private static final Identifier DEFAULT_FONT = Text.empty().getStyle().getFont();

    public SignText changeFont(SignText text, Identifier itemFont) {
        for (int i = 0; i < 4; i++) {
            Text message = text.getMessage(i, false);
            if (message.getString().isEmpty()) continue;
            Identifier currentFont = message.getStyle().getFont();

            Identifier font;
            if (currentFont.equals(itemFont)) font = DEFAULT_FONT;
            else font = itemFont;

            text = text.withMessage(i, text.getMessage(i, false).copy().styled(t -> t.withFont(font)));
        }
        return text;
    }

}