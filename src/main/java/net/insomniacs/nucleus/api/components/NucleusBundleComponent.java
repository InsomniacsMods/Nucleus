package net.insomniacs.nucleus.api.components;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.api.items.NucleusBundleItem;
import net.insomniacs.nucleus.impl.components.NucleusComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public record NucleusBundleComponent(
		int capacity,
		List<ItemStack> contents,
		int occupancy
) {

	public static NucleusBundleComponent empty(int capacity) {
		return new NucleusBundleComponent(capacity, List.of());
	}

	public static final NucleusBundleComponent EMPTY = new NucleusBundleComponent(64, List.of());


	public static final Codec<NucleusBundleComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.optionalFieldOf("capacity", 64).forGetter(NucleusBundleComponent::capacity),
			ItemStack.CODEC.listOf().optionalFieldOf("contents", List.of()).forGetter(NucleusBundleComponent::contents),
			Codec.INT.optionalFieldOf("itemOccupancy", 0).forGetter(NucleusBundleComponent::occupancy)
	).apply(instance, NucleusBundleComponent::new));

	public NucleusBundleComponent(int capacity, List<ItemStack> contents) {
		this(capacity, contents, 0);
	}


	public float getBundleOccupancy() {
		return (float)occupancy / capacity;
	}

	public Builder modify(NucleusBundleItem bundle) {
		List<ItemStack> contents = new ArrayList<>(this.contents);
		return new Builder(bundle, capacity, contents, occupancy);
	}


	public static class Builder {

		private final NucleusBundleItem bundle;
		private final int capacity;
		private final List<ItemStack> contents;
		private int occupancy;

		private Builder(
				NucleusBundleItem bundle,
				int capacity,
				List<ItemStack> contents,
				int occupancy
		) {
			this.bundle = bundle;
			this.capacity = capacity;
			this.contents = contents;
			this.occupancy = occupancy;
		}


		public int getItemOccupancy(ItemStack stack) {
			return bundle.getItemOccupancy(stack);
		}

		public boolean acceptsItem(ItemStack stack) {
			return bundle.acceptsItem(stack);
		}



		public int allowedCount(ItemStack stack) {
			int allowedOccupancy = capacity - occupancy;
			int itemOccupancy = bundle.getItemOccupancy(stack);
			int singleOccupancy = bundle.getItemOccupancy(stack.copyWithCount(1));
			float occupancyPerStack = (float)(singleOccupancy * stack.getCount()) / itemOccupancy;
			return (int)Math.min(
					allowedOccupancy * occupancyPerStack,
					stack.getCount()
			);
		}


		public ItemStack addItem(ItemStack originalStack) {
			if (originalStack.isEmpty()) return originalStack;
			if (!acceptsItem(originalStack)) return originalStack;

			int allowedCount = allowedCount(originalStack);
			if (allowedCount <= 0) return originalStack;
			ItemStack stack = originalStack.copyWithCount(allowedCount);

			int stackOccupancy = getItemOccupancy(stack);
			if (stackOccupancy + occupancy > capacity) return ItemStack.EMPTY;

			addInternal(stack);
			occupancy += stackOccupancy;
			originalStack.decrement(allowedCount);
			return originalStack;
		}

		public void addInternal(ItemStack stack) {
			if (stack.isStackable()) for (int i = 0; i < contents.size(); ++i) {
				if (stack.getCount() <= 0) return;
				ItemStack containedStack = contents.get(i);
				int count = containedStack.getCount();
				int maxCount = containedStack.getMaxCount();
				if (count >= maxCount) continue;
				if (!ItemStack.areItemsAndComponentsEqual(containedStack, stack)) continue;

				int acceptedCount = Math.min(maxCount - count, stack.getCount());
				containedStack.increment(acceptedCount);
				stack.decrement(acceptedCount);
				contents.set(i, containedStack);
			}
			if (stack.getCount() > 0) {
				contents.addLast(stack);
			}
		}

		public ItemStack removeFirst() {
			if (contents.isEmpty()) return ItemStack.EMPTY;

			ItemStack stack = contents.removeFirst();
			int stackOccupancy = bundle.getItemOccupancy(stack);
			occupancy -= stackOccupancy;

			return stack;
		}

		public List<ItemStack> clear() {
			var result = new ArrayList<>(contents);
			contents.clear();
			occupancy = 0;
			return result;
		}


		public void updateBundle(ItemStack stack) {
			stack.set(
					NucleusComponents.BUNDLE,
					new NucleusBundleComponent(capacity, contents, occupancy)
			);
		}

	}

	public boolean isEmpty() {
		return this.contents.isEmpty();
	}

	public Iterable<ItemStack> iterateCopy() {
		return Lists.transform(this.contents, ItemStack::copy);
	}

	public JsonObject toJson() {
		return CODEC.encodeStart(JsonOps.INSTANCE, this).getOrThrow().getAsJsonObject();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof NucleusBundleComponent component)) return false;
		return this.capacity == component.capacity && this.contents == component.contents;
	}

	@Override
	public int hashCode() {
		int result = capacity;
		result += this.contents.stream().mapToInt(Object::hashCode).sum();
		return result;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + toJson().getAsString();
	}

}
