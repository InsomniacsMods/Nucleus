package net.insomniacs.nucleus.impl.items;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.SignText;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignChangingItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class SignFontChangingItem extends Item implements SignChangingItem {

    private static final Identifier DEFAULT_FONT = Text.empty().getStyle().getFont();
    private final Identifier font;
    private final SoundEvent sound;

    public SignFontChangingItem(Item.Settings settings, Identifier font, SoundEvent sound) {
        super(settings);
        this.font = font;
        this.sound = sound;
    }

    @Override
    public boolean useOnSign(World world, SignBlockEntity signBlock, boolean front, PlayerEntity player) {
        if (signBlock.changeText(text -> changeFont(text, player.getActiveItem()), front)) {
            world.playSound(null, signBlock.getPos(), this.sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
            return true;
        }
        return false;
    }

    @SuppressWarnings("all")
    public SignText changeFont(SignText text, ItemStack stack) {
        for (int i = 0; i < 4; i++) {
            Identifier currentFont = text.getMessage(i, false).getStyle().getFont();
            Identifier font;
            if (currentFont != DEFAULT_FONT) font = DEFAULT_FONT;
            else getFont(stack.getOrCreateNbt());
            text = text.withMessage(i, text.getMessage(i, false).copy().styled(t -> t.withFont(this.font)));
        }
        return text;
    }

    private Identifier getFont(NbtCompound nbt) {
        if (!nbt.contains("font")) return this.font;
        return new Identifier(nbt.getString("font"));
    }

    private MutableText getFontName(NbtCompound nbt) {
        Identifier font = getFont(nbt);
        String fontKey = font.toTranslationKey("font");
        return Text.translatable(fontKey).styled(t -> t.withFont(font));
    }

    public static final Style TEXT_STYLE = Style.EMPTY.withColor(new Color(166, 192, 166).getRGB());
    public static final Style PART_STYLE = Style.EMPTY.withColor(new Color(123, 191, 123).getRGB());

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(ScreenTexts.EMPTY);
        Text button = Text.keybind("key.use").fillStyle(PART_STYLE);
        Text fontName = getFontName(stack.getOrCreateNbt()).fillStyle(PART_STYLE);
        tooltip.add(Text.translatable("item.manic.grim_quill.description", button, fontName).fillStyle(TEXT_STYLE.withItalic(true)));
    }
}