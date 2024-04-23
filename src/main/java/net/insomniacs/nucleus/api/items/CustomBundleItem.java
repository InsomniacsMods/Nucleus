package net.insomniacs.nucleus.api.items;

import net.insomniacs.nucleus.api.components.NucleusComponents;
import net.insomniacs.nucleus.api.components.custom.bundle.CustomBundleComponent;
import net.insomniacs.nucleus.api.utils.BundleItemSoundGroup;
import net.insomniacs.nucleus.api.content.NucleusSoundEvents;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class CustomBundleItem extends BundleItem {

	public static float getAmountFilled(ItemStack stack, World w, LivingEntity e, int s) {
		var component = getComponent(stack);
		return component.getBundleOccupancy();
	}

	public BundleItemSoundGroup soundGroup;

	public CustomBundleItem(Settings settings, BundleItemSoundGroup soundGroup) {
		super(settings);
		this.soundGroup = soundGroup;
	}

	public static CustomBundleComponent getComponent(ItemStack stack) {
		return stack.getOrDefault(NucleusComponents.BUNDLE_CONTENTS, CustomBundleComponent.EMPTY);
	}

	// Override

	public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
		if (clickType != ClickType.RIGHT) return false;

		var component = getComponent(stack);
		ItemStack clickedStack = slot.getStack();
		var builder = component.modify();

		if (stack.isEmpty()) removeItem(player, builder, slot);
		else addItem(player, builder, clickedStack);

		builder.updateBundle(stack);
		return true;
	}

	public void removeItem(PlayerEntity player, CustomBundleComponent.Builder component, Slot slot) {
		ItemStack removedItem = component.removeFirst();
		this.playRemoveOneSound(player);
		if (removedItem.isEmpty()) return;

		ItemStack addedItem = slot.insertStack(removedItem);
		component.addItem(addedItem);
	}

	public boolean onClicked(ItemStack stack, ItemStack clickedStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursor) {
		if (clickType == ClickType.LEFT) return false;
		if (!slot.canTakePartial(player)) return false;

		var component = getComponent(stack);
		var builder = component.modify();

		if (clickedStack.isEmpty()) removeItem(player, builder, cursor);
		else addItem(player, builder, clickedStack);

		builder.updateBundle(stack);
		return true;
	}

	public void removeItem(PlayerEntity player, CustomBundleComponent.Builder component, StackReference cursor) {
		ItemStack itemStack = component.removeFirst();
		if (itemStack == null) return;

		this.playRemoveOneSound(player);
		cursor.set(itemStack);
	}

	public void addItem(PlayerEntity player, CustomBundleComponent.Builder component, ItemStack stack) {
		stack = component.addItem(stack);
		if (stack.isEmpty()) return;

		this.playInsertSound(player);
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		var component = getComponent(stack);
		if (component.isEmpty()) return TypedActionResult.fail(stack);

		component.modify().clear().updateBundle(stack);
		this.playDropContentsSound(user);
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		return TypedActionResult.success(stack, world.isClient());
	}

	@Override
	public void appendTooltip(ItemStack s, Item.TooltipContext c, List<Text> l, TooltipType t) {}

	// Sound

	public CustomBundleItem(Settings settings) {
		this(settings, NucleusSoundEvents.BUNDLE_ITEM);
	}

	private void playRemoveOneSound(Entity entity) {
		playSound(entity, soundGroup.removeSound());
	}

	private void playInsertSound(Entity entity) {
		playSound(entity, soundGroup.addSound());
	}

	private void playDropContentsSound(Entity entity) {
		playSound(entity, soundGroup.dropSound());
	}

	private void playSound(Entity entity, SoundEvent sound) {
		entity.playSound(sound, soundGroup.volume(), soundGroup.pitch() + entity.getWorld().getRandom().nextFloat() * 0.4F);
	}

}
