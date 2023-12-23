package net.insomniacs.nucleus.impl.mixins.common;

import net.insomniacs.nucleus.asm.NucleusItemStackAccess;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "getTooltip", at = @At("RETURN"), cancellable = true)
    private void manicSoulboundTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
        List<Text> texts = cir.getReturnValue();
        addSoulboundTooltip(texts);
        cir.setReturnValue(texts);
    }

    @Unique
    private static final int SOULBOUND_COLOR = Color.decode("#A9CCCA").getRGB();

    @Unique
    public void addSoulboundTooltip(List<Text> texts) {
        if (!((NucleusItemStackAccess)this).isSoulbound()) return;
        Text text = Text.translatable("ui.nucleus.soulbound").styled(style -> style.withItalic(true).withColor(SOULBOUND_COLOR));
        texts.add(text);
    }

}