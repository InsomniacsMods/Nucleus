package net.insomniacs.nucleus.impl.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MobSpawningItem extends Item {

    private final EntityType<?> entity;
    private final Random random;

    public EntityType<?> getEntityType(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound compound = getEntityNbt(nbt);
        System.out.println(compound);
        if (!compound.contains("id")) return this.entity;

        return EntityType.get(compound.getString("id")).orElse(this.entity);
    }

    public static NbtCompound getEntityNbt(NbtCompound nbt) {
        if (!nbt.contains("EntityTag")) return new NbtCompound();
        return nbt.getCompound("EntityTag");
    }

    public static int SPAWN_RADIUS = 5;

    public MobSpawningItem(Settings settings, @Nullable EntityType<?> entity) {
        super(settings);
        this.entity = entity;
        this.random = new Random();
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entity) {

        ItemStack finished = super.finishUsing(stack, world, entity);

        if (world.isClient) return finished;

        BlockPos pos = entity.getBlockPos();
        double angle = Math.toRadians( this.random.nextInt(360) );
        double x = Math.cos(angle) * SPAWN_RADIUS;
        double y = 0;
        double z = Math.sin(angle) * SPAWN_RADIUS;
        // TODO make y the closest spawnable block
        pos = pos.add((int)x, (int)y, (int)z);

        EntityType<?> type = getEntityType(stack);
        Entity spawnedEntity = type.spawnFromItemStack((ServerWorld)world, stack, (PlayerEntity)entity, pos, SpawnReason.SPAWN_EGG, true, false);
        postSpawn(world, pos, spawnedEntity);

        return finished;
    }

    public void postSpawn(World world, BlockPos pos, Entity entity) {};

    private Text getEntityName(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound compound = getEntityNbt(nbt);
        if (compound.isEmpty()) {
            assert this.entity != null;
            return this.entity.getName();
        }
        if (!compound.contains("CustomName")) return getEntityType(stack).getName();
        String rawName = compound.getString("CustomName");

        MutableText result = Text.Serialization.fromJson(rawName);
        if (result == null) return Text.empty();
        return result.formatted(Formatting.GRAY);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable("ui.nucleus.when_eaten").formatted(Formatting.GRAY));
        tooltip.add(spawningTooltip(stack));
    }

    public Text spawningTooltip(ItemStack stack) {
        if (this.entity == null) return Text.translatable("item.nucleus.mob_spawning_item.unscented").formatted(Formatting.GRAY);
        return Text.translatable("item.nucleus.mob_spawning_item.description", getEntityName(stack));
    }
}