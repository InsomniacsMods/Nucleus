package net.insomniacs.nucleus.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.insomniacs.nucleus.api.components.ComponentTooltipRegistry;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.DataComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TooltipAppender;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

	@Shadow
	private <T extends TooltipAppender> void appendTooltip(DataComponentType<T> componentType, Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type) {}

	@ModifyReturnValue(at = @At("RETURN"), method = "getTooltip")
	public List<Text> modifyGetTooltip(List<Text> original, @Local(argsOnly = true) Item.TooltipContext context, @Local(argsOnly = true) TooltipType type) {
		var index = new AtomicInteger(1);
		Consumer<Text> appender = text -> original.add(index.getAndIncrement(), text);
		ComponentTooltipRegistry.getComponents().forEach(comp -> appendTooltip(comp, context, appender, type));
		return original;
	}

}
