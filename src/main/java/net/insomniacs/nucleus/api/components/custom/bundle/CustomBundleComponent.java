package net.insomniacs.nucleus.api.components.custom.bundle;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.api.components.NucleusComponents;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CustomBundleComponent {

	public static final CustomBundleComponent EMPTY = new CustomBundleComponent(64, List.of());

	public static final Codec<CustomBundleComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.optionalFieldOf("capacity", 64).forGetter(null),
			ItemStack.CODEC.listOf().optionalFieldOf("contents", List.of()).forGetter(null)
	).apply(instance, CustomBundleComponent::new));


	private final int capacity;
	private final List<ItemStack> contents;
	private int occupancy;

	public CustomBundleComponent(int capacity, List<ItemStack> contents, int occupancy) {
		this.capacity = capacity;
		this.contents = contents;
		this.occupancy = occupancy;
	}

	public CustomBundleComponent(int capacity, List<ItemStack> contents) {
		this(capacity, contents, 0);
	}

	public float getBundleOccupancy() {
		return (float)occupancy / capacity;
	}

	public int getItemOccupancy(ItemStack stack) {
		return 1;
	}

	public boolean acceptsItem(ItemStack stack) {
		return true;
	}

	public Builder modify() {
		return new Builder(this);
	}


	public static class Builder {

		private final CustomBundleComponent base;

		public Builder(CustomBundleComponent base) {
			this.base = base;
		}

		public Builder clear() {
			base.contents.clear();
			return this;
		}

		public ItemStack addItem(ItemStack stack) {
			if (stack.isEmpty()) return stack;
			if (!base.acceptsItem(stack)) return ItemStack.EMPTY;

			int stackOccupancy = base.getItemOccupancy(stack);
			if (stackOccupancy + base.occupancy > base.capacity) return ItemStack.EMPTY;

			base.contents.add(stack);
			base.occupancy += stackOccupancy;
			return stack;
		}

		public ItemStack removeFirst() {
			if (base.isEmpty()) return ItemStack.EMPTY;

			ItemStack stack = base.contents.removeFirst().copy();
			int stackOccupancy = base.getItemOccupancy(stack);

			base.occupancy -= stackOccupancy;
			return stack;
		}

		public CustomBundleComponent copy() {
			return new CustomBundleComponent(base.capacity, base.contents, base.occupancy);
		}

		public void updateBundle(ItemStack stack) {
			stack.set(NucleusComponents.BUNDLE_CONTENTS, copy());
		}

	}


	public ItemStack get(int index) {
		return this.contents.get(index);
	}

	public boolean isEmpty() {
		return this.contents.isEmpty();
	}


	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof CustomBundleComponent)) return false;
		return this.hashCode() == object.hashCode();
	}


	public int hashCode() {
		return capacity + ItemStack.listHashCode(this.contents);
	}

	public JsonObject toJson() {
		return CODEC.encodeStart(JsonOps.INSTANCE, this).getOrThrow().getAsJsonObject();
	}

	public String toString() {
		return this.getClass().getName() + toJson().getAsString();
	}

}
