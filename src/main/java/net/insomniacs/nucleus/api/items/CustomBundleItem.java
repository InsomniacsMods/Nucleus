package net.insomniacs.nucleus.api.items;

import net.insomniacs.nucleus.api.components.BundleComponent;
import net.insomniacs.nucleus.api.content.NucleusSoundEvents;
import net.insomniacs.nucleus.api.sound.BundleSoundGroup;
import net.insomniacs.nucleus.impl.components.NucleusComponents;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.screen.slot.Slot;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public abstract class CustomBundleItem extends BundleItem {

	public BundleSoundGroup soundGroup;
	@Nullable public Integer defaultCapacity;

	public CustomBundleItem(Settings settings, @Nullable Integer defaultCapacity, BundleSoundGroup soundGroup) {
		super(settings);
		this.soundGroup = soundGroup;
		this.defaultCapacity = defaultCapacity;
	}

	public CustomBundleItem(Settings settings, BundleSoundGroup soundGroup) {
		this(settings, null, soundGroup);
	}

	public CustomBundleItem(Settings settings, int defaultCapacity) {
		this(settings, defaultCapacity, NucleusSoundEvents.BUNDLE_ITEM);
	}

	public CustomBundleItem(Settings settings) {
		this(settings, null, NucleusSoundEvents.BUNDLE_ITEM);
	}

	public static BundleComponent getComponent(ItemStack stack) {
		return stack.getOrDefault(NucleusComponents.BUNDLE, BundleComponent.EMPTY);
	}

	public abstract int getItemOccupancy(ItemStack stack);

	public abstract boolean acceptsItem(ItemStack stack);

	public static float getOccupancyPredicate(ItemStack stack, World w, LivingEntity e, int s) {
		var component = getComponent(stack);
		return component.getBundleOccupancy();
	}

	// Functions

	@Override
	public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
		if (clickType != ClickType.RIGHT) return false;

		var component = getComponent(stack);
		ItemStack clickedStack = slot.getStack();
		var builder = component.modify(this);

		if (stack.isEmpty()) removeFromBundle(player, builder, slot);
		else addToBundle(player, builder, clickedStack);

		builder.updateBundle(stack);
		return true;
	}

	@Override
	public boolean onClicked(ItemStack stack, ItemStack clickedStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursor) {
		if (clickType != ClickType.RIGHT) return false;
		if (!slot.canTakePartial(player)) return false;

		var component = getComponent(stack);
		var builder = component.modify(this);


		if (clickedStack.isEmpty()) removeToCursor(player, builder, cursor);
		else addToBundle(player, builder, clickedStack);

		builder.updateBundle(stack);
		return true;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);
		var component = getComponent(stack);
		if (component.isEmpty()) return TypedActionResult.fail(stack);

		var builder = component.modify(this);
		List<ItemStack> items = builder.clear();
		builder.updateBundle(stack);

		items.forEach(s -> player.dropItem(s, true));
		soundGroup.playDropSound(player);
		player.incrementStat(Stats.USED.getOrCreateStat(this));
		return TypedActionResult.success(stack, world.isClient());
	}

	@Override
	public void onItemEntityDestroyed(ItemEntity entity) {
		var component = getComponent(entity.getStack());
		if (component.isEmpty()) return;
		entity.getStack().set(NucleusComponents.BUNDLE, BundleComponent.EMPTY);
		ItemUsage.spawnItemContents(entity, component.iterateCopy());
	}

	public void addToBundle(PlayerEntity player, BundleComponent.Builder builder, ItemStack stack) {
		stack = builder.addItem(stack);
		if (stack.isEmpty()) return;

		soundGroup.playAddSound(player);
	}

	public void removeFromBundle(PlayerEntity player, BundleComponent.Builder builder, Slot slot) {
		ItemStack removedStack = builder.removeFirst();
		soundGroup.playRemoveSound(player);
		if (removedStack.isEmpty()) return;

		ItemStack addedItem = slot.insertStack(removedStack);
		builder.addItem(addedItem);
	}

	public void removeToCursor(PlayerEntity player, BundleComponent.Builder builder, StackReference cursor) {
		ItemStack removedStack = builder.removeFirst();
		if (removedStack == null) return;

		soundGroup.playRemoveSound(player);
		cursor.set(removedStack);
	}

	// Visual

	@Override
	public boolean isItemBarVisible(ItemStack stack) {
		var component = getComponent(stack);
		return !component.isEmpty();
	}

	@Override
	public int getItemBarStep(ItemStack stack) {
		var component = getComponent(stack);
		int step = (component.occupancy() / component.capacity()) * 13;
		return Math.min(step, 13);
	}

	@Override
	public Optional<TooltipData> getTooltipData(ItemStack stack) {
		if (stack.contains(DataComponentTypes.HIDE_TOOLTIP)) return Optional.empty();
		if (stack.contains(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP)) return Optional.empty();

		var component = getComponent(stack);
		return Optional.ofNullable(component).map(CustomBundleTooltipData::new);
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		var component = getComponent(stack);
		Text text = Text
				.translatable("item.minecraft.bundle.fullness", component.occupancy(), component.capacity())
				.formatted(Formatting.GRAY);
		tooltip.add(text);
	}

	//

}
