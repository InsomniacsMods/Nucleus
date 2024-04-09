package net.insomniacs.nucleus.api.modreg.entries;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.insomniacs.nucleus.api.modreg.ModEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityEntry extends ModEntry<EntityEntry, EntityEntry.Builder, EntityType<?>> {

	@Override
	public Registry<EntityType<?>> getRegistry() {
		return Registries.ENTITY_TYPE;
	}

	@Override
	protected EntityType<?> generateValue() {
		return settings.entityBuilder
				.build();
	}

	@Override
	protected EntityType<?> register() {
		var result = super.register();
		FabricDefaultAttributeRegistry.register((EntityType<? extends LivingEntity>)result, settings.attributeBuilder.build());
		return result;
	}

	public EntityEntry(Builder settings) {
		super(settings);
	}

	public static class Builder extends ModEntry.EntryBuilder<Builder, EntityEntry, EntityType<?>> {

		@Override
		protected EntityEntry createEntry() {
			return new EntityEntry(this);
		}

		protected FabricEntityTypeBuilder<?> entityBuilder;
		protected DefaultAttributeContainer.Builder attributeBuilder;

		public Builder(Identifier id) {
			super(id);
			entityBuilder = FabricEntityTypeBuilder.create();
			attributeBuilder = DefaultAttributeContainer.builder();
		}

		// Attributes

		public Builder addAttribute(EntityAttribute attribute) {
			this.attributeBuilder.add(attribute);
			return this;
		}

		public Builder addAttribute(EntityAttribute attribute, int value) {
			this.attributeBuilder.add(attribute, value);
			return this;
		}

		public Builder health() {
			return addAttribute(EntityAttributes.GENERIC_MAX_HEALTH);
		}

		public Builder health(int value) {
			return addAttribute(EntityAttributes.GENERIC_MAX_HEALTH, value);
		}

		public Builder speed() {
			return addAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		}

		public Builder speed(int value) {
			return addAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, value);
		}

		public Builder armor() {
			return addAttribute(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
		}

		public Builder armor(int value) {
			return addAttribute(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, value);
		}

		public Builder damage(int value) {
			return addAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE, value);
		}

		public Builder damage() {
			return addAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		}

		public Builder knockback(int value) {
			return addAttribute(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, value);
		}

		public Builder knockback() {
			return addAttribute(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
		}

		public Builder attackSpeed(int value) {
			return addAttribute(EntityAttributes.GENERIC_ATTACK_SPEED, value);
		}

		public Builder attackSpeed() {
			return addAttribute(EntityAttributes.GENERIC_ATTACK_SPEED);
		}

		public Builder knockbackResistance() {
			return addAttribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
		}

		public Builder knockbackResistance(int value) {
			return addAttribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, value);
		}

		// Settings

		public Builder attackSpeed() {
			return addAttribute(EntityAttributes.GENERIC_ATTACK_SPEED);
		}

	}


}
