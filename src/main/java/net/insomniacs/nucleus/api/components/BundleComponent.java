package net.insomniacs.nucleus.api.components;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.api.items.CustomBundleItem;
import net.insomniacs.nucleus.impl.components.NucleusComponents;
import net.minecraft.item.ItemStack;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public record BundleComponent(
		int capacity,
		List<ItemStack> contents,
		int occupancy
) {

	public static final BundleComponent EMPTY = new BundleComponent(64, List.of());

	public static final Codec<BundleComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.optionalFieldOf("capacity", 64).forGetter(BundleComponent::capacity),
			ItemStack.CODEC.listOf().optionalFieldOf("contents", List.of()).forGetter(BundleComponent::contents),
			Codec.INT.optionalFieldOf("occupancy", 0).forGetter(BundleComponent::occupancy)
	).apply(instance, BundleComponent::new));

	public BundleComponent(int capacity, List<ItemStack> contents) {
		this(capacity, contents, 0);
	}

	public float getBundleOccupancy() {
		return (float)occupancy / capacity;
	}

	public Builder modify(CustomBundleItem bundle) {
		int capacity;
		if (bundle.defaultCapacity != null) capacity = bundle.defaultCapacity;
		else capacity = this.capacity;

		List<ItemStack> contents = new ArrayList<>(this.contents);

		return new Builder(bundle, capacity, contents, this.occupancy);
	}

	public static class Builder {

		private final CustomBundleItem bundle;
		private final int capacity;
		private final List<ItemStack> contents;
		private int occupancy;

		private Builder(
				CustomBundleItem bundle,
				int capacity,
				List<ItemStack> contents,
				int occupancy
		) {
			this.bundle = bundle;
			this.capacity = capacity;
			this.contents = contents;
			this.occupancy = occupancy;
		}

		public List<ItemStack> clear() {
			var result = new ArrayList<>(contents);
			contents.clear();
			occupancy = 0;
			return result;
		}

		public ItemStack addItem(ItemStack stack) {
			if (stack.isEmpty()) return stack;
			if (!bundle.acceptsItem(stack)) return ItemStack.EMPTY;

			int stackOccupancy = bundle.getItemOccupancy(stack);
			if (stackOccupancy + occupancy > capacity) return ItemStack.EMPTY;

			contents.add(stack);
			occupancy += stackOccupancy;
			return stack;
		}

		public ItemStack removeFirst() {
			if (contents.isEmpty()) return ItemStack.EMPTY;

			ItemStack stack = contents.removeFirst().copy();
			int stackOccupancy = bundle.getItemOccupancy(stack);

			occupancy -= stackOccupancy;
			return stack;
		}

		public BundleComponent copy() {
			return new BundleComponent(capacity, contents, occupancy);
		}

		public void updateBundle(ItemStack stack) {
			stack.set(NucleusComponents.BUNDLE, copy());
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
		if (!(object instanceof BundleComponent component)) return false;
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
