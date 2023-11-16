package net.insomniacs.nucleus.impl.items;

import net.minecraft.client.item.TooltipContext;
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
import java.util.concurrent.ThreadLocalRandom;

public class MobSpawningItem extends Item {

    private final EntityType<?> entity;

    public EntityType<?> getEntityType(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound compound = getEntityNbt(nbt);
        if (!compound.contains("id")) return this.entity;

        return EntityType.get(compound.getString("id")).orElse(this.entity);
    }

    public static NbtCompound getEntityNbt(NbtCompound nbt) {
        if (!nbt.contains("entity_tag")) return new NbtCompound();
        return nbt.getCompound("entity_tag");
    }

    public static int SPAWN_RADIUS = 50;

    public MobSpawningItem(Settings settings, @Nullable EntityType<?> entity) {
        super(settings);
        this.entity = entity;
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

    private Text getEntityName(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound compound = getEntityNbt(nbt);
        if (compound.isEmpty()) {
            assert this.entity != null;
            return this.entity.getName();
        }
        if (!compound.contains("CustomName")) return getEntityType(stack).getName();
        String rawName = compound.getString("CustomName");

        MutableText result = Text.Serializer.fromJson(rawName);
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