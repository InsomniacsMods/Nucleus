package net.insomniacs.nucleus.api.items;

import net.insomniacs.nucleus.api.components.NucleusBundleComponent;
import net.insomniacs.nucleus.api.content.NucleusSoundEvents;
import net.insomniacs.nucleus.api.content.NucleusTags;
import net.insomniacs.nucleus.api.sound.BundleSoundGroup;
import net.insomniacs.nucleus.impl.components.NucleusComponents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public abstract class NucleusBundleItem extends BundleItem {

	private final BundleSoundGroup soundGroup;
	private final int defaultCapacity;

	public NucleusBundleItem(Settings settings, int defaultCapacity, BundleSoundGroup soundGroup) {
		super(settings);
		this.soundGroup = soundGroup;
		this.defaultCapacity = defaultCapacity;
	}

	public NucleusBundleItem(Settings settings, int defaultCapacity) {
		this(settings, defaultCapacity, NucleusSoundEvents.BUNDLE_ITEM);
	}


	public static NucleusBundleComponent getComponent(ItemStack stack) {
		int capacity = 64;
		if (stack.getItem() instanceof NucleusBundleItem bundle) capacity = bundle.defaultCapacity;
		return stack.getOrDefault(NucleusComponents.BUNDLE, NucleusBundleComponent.empty(capacity));
	}


	public int getItemOccupancy(ItemStack stack) {
		return stack.getCount() * (64 / stack.getMaxCount());
	}

	public boolean acceptsItem(ItemStack stack) {
		if (!stack.getItem().canBeNested()) return false;
		if (stack.isIn(NucleusTags.CANNOT_BE_INSERTED_INTO_BUNDLES)) return false;
		return !stack.isEmpty();
	}


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

		if (clickedStack.isEmpty()) removeFromBundle(player, builder, slot);
		else {
			ItemStack leftoverStack = addToBundle(player, builder, clickedStack);
			slot.insertStack(leftoverStack);
		}

		builder.updateBundle(stack);
		return true;
	}

	@Override
	public boolean onClicked(ItemStack stack, ItemStack clickedStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursor) {
		if (clickType != ClickType.RIGHT) return false;
		if (!slot.canTakePartial(player)) return false;

		var component = getComponent(stack);
		var builder = component.modify(this);


		if (clickedStack.isEmpty()) putInSlot(player, builder, cursor);
		else {
			addToBundle(player, builder, clickedStack);
		}

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
		entity.getStack().set(NucleusComponents.BUNDLE, NucleusBundleComponent.EMPTY);
		ItemUsage.spawnItemContents(entity, component.iterateCopy());
	}

	public ItemStack addToBundle(PlayerEntity player, NucleusBundleComponent.Builder builder, ItemStack stack) {
		soundGroup.playAddSound(player);
		var result = builder.addItem(stack);
		return result;
	}

	public void removeFromBundle(PlayerEntity player, NucleusBundleComponent.Builder builder, Slot slot) {
		ItemStack removedStack = builder.removeFirst();
		soundGroup.playRemoveSound(player);
		if (removedStack.isEmpty()) return;

		ItemStack addedItem = slot.insertStack(removedStack);
		builder.addItem(addedItem);
	}

	public void putInSlot(PlayerEntity player, NucleusBundleComponent.Builder builder, StackReference cursor) {
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
		int step = (int)(component.getBundleOccupancy() * 13);
		return Math.min(step, 13);
	}



	@Override
	public Optional<TooltipData> getTooltipData(ItemStack stack) {
		if (stack.contains(DataComponentTypes.HIDE_TOOLTIP)) return Optional.empty();
		if (stack.contains(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP)) return Optional.empty();

		var component = getComponent(stack);
		return Optional.ofNullable(component).map(NucleusBundleTooltipData::new);
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		var component = getComponent(stack);
		tooltip.add(getTooltip(component));
	}

	public MutableText getTooltip(NucleusBundleComponent component) {
		return Text.translatable("item.minecraft.bundle.fullness", component.occupancy(), component.capacity())
				.formatted(Formatting.GRAY);
	}

}
