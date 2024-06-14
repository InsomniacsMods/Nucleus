package net.insomniacs.nucleus.api.utils;

import com.google.common.base.Supplier;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

public record ToolMaterialImpl (
		int durability,
		float miningSpeed,
		float attackDamage,
		TagKey<Block> inverseTag,
		int enchantability,
		Supplier<Ingredient> repairIngredient
) implements ToolMaterial {

	@Override
	public int getDurability() {
		return durability;
	}

	@Override
	public float getMiningSpeedMultiplier() {
		return miningSpeed;
	}

	@Override
	public float getAttackDamage() {
		return attackDamage;
	}

	@Override
	public TagKey<Block> getInverseTag() {
		return inverseTag;
	}

	@Override
	public int getEnchantability() {
		return enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return repairIngredient.get();
	}

}