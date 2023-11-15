package net.insomniacs.nucleus.impl.items;

import net.insomniacs.nucleus.api.utils.Cache;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MobSpawningFoodItem extends Item {

    private final EntityType<?> entity;
    private final Text entityName;

    public static int SPAWN_RADIUS = 50;

    public MobSpawningFoodItem(FoodComponent food, Settings settings, EntityType<?> entity) {
        super(settings.food(food));
        this.entity = entity;
        this.entityName = entity.getName();
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entity) {

        ItemStack finished = super.finishUsing(stack, world, entity);

        if (world.isClient) return finished;

        BlockPos pos = entity.getBlockPos();
        int x = ThreadLocalRandom.current().nextInt(0, SPAWN_RADIUS + 1);
        int z = SPAWN_RADIUS - x;
        pos.add(x, 0, z);
        // TODO maybe make the radius an actual circle instead of whatever the hell this is

        EntityType<?> spawnedEntity = getEntityType(stack);
        spawnedEntity.spawnFromItemStack((ServerWorld)world, stack, (PlayerEntity)entity, pos, SpawnReason.SPAWN_EGG, false, false);

        return finished;
    }

    public static NbtCompound getEntityNbt(NbtCompound nbt) {
        if (!nbt.contains("entity_tag")) return new NbtCompound();
        return nbt.getCompound("entity_tag");
    }

    public EntityType<?> getEntityType(ItemStack stack) {

        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound compound = getEntityNbt(nbt);
        if (!compound.contains("id")) return this.entity;

        return EntityType.get(compound.getString("id")).orElse(this.entity);
    }

    private static final Cache<Integer, Text> ENTITY_NAME_CACHE = new Cache<>(3);

    private Text getEntityName(ItemStack stack) {

        NbtCompound nbt = stack.getOrCreateNbt();
        int hash = stack.hashCode();
        if (ENTITY_NAME_CACHE.containsKey(hash)) return ENTITY_NAME_CACHE.get(hash);

        NbtCompound compound = getEntityNbt(nbt);
        if (compound.isEmpty()) return this.entityName;
        if (!compound.contains("CustomName")) return getEntityType(stack).getName();
        String rawName = compound.getString("CustomName");

        Text result = Text.Serializer.fromJson(rawName);

        ENTITY_NAME_CACHE.put(hash, result);
        return result;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable("ui.nucleus.when_eaten").formatted(Formatting.GRAY));

        if (this.entity == null) {
            tooltip.add(Text.translatable("item.nucleus.mob_spawning_food.unscented"));
        } else {
            tooltip.add(Text.translatable("item.nucleus.mob_spawning_food.description", getEntityName(stack)));
        }
    }
}