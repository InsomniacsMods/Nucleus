package net.insomniacs.nucleus.impl.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LocationBindingItem extends Item implements HasPredicates {

    public static final String BIND_DATA = "location";
    public static final String IS_BOUND_KEY = "bound";


    public static final List<LocationBindingItem> ITEMS = new ArrayList<>();

    public LocationBindingItem(Item.Settings settings) {
        super(settings);
        ITEMS.add(this);
    }

    public float registerPredicates(ItemStack stack, World world, LivingEntity entity, int i) {
        return hasLocation(stack);
    }

//    public static void registerPredicates() {
//        ITEMS.forEach(item -> ModItemPredicates.registerPredicate(item, "has_location",
//                (stack, level, entity, i) -> hasLocation(stack)
//        ));
//        ITEMS.clear();
//    }

    public static float hasLocation(ItemStack stack) {
        return stack.getOrCreateNbt().contains(BIND_DATA) ? 1F : 0F;
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getSide() == Direction.DOWN) return ActionResult.FAIL;

        ItemPlacementContext placement = new ItemPlacementContext(context);
        BlockPos pos = placement.getBlockPos();
        World world = context.getWorld();
        if (world.getBlockState(pos.down()).isAir()) return ActionResult.FAIL;


        ItemStack stack = context.getStack();
        NbtCompound compound = stack.getOrCreateNbt();
        RegistryKey<World> worldKey = world.getRegistryKey();

        writeNbt(worldKey, pos, compound);

        return ActionResult.SUCCESS;
    }

    private void writeNbt(RegistryKey<World> worldKey, BlockPos pos, NbtCompound nbt) {
        nbt.put(BIND_DATA, Location.CODEC.encodeStart(NbtOps.INSTANCE, new Location(pos, worldKey)).get().orThrow());
        nbt.putBoolean(IS_BOUND_KEY, true);
    }

    public static final Style TEXT_STYLE = Style.EMPTY.withColor(new Color(166, 192, 166).getRGB());

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains(IS_BOUND_KEY)) tooltip.add(locationTooltip(nbt));
    }

    public static Text locationTooltip(NbtCompound nbt) {
        if(!nbt.contains(BIND_DATA))
            return Text.of("You shouldn't be seeing this.");
        Location data = Location.CODEC.parse(NbtOps.INSTANCE, nbt.get(BIND_DATA)).get().orThrow();
        BlockPos pos = data.pos();
        Text dimension = Text.translatable(data.worldRegistryKey().getValue().toTranslationKey("dimension"));


        return Text.translatable("item.manic.anima_shackles.location",
                dimension, pos.getX(), pos.getY(), pos.getZ()
        ).fillStyle(TEXT_STYLE);
    }

    public record Location(BlockPos pos, RegistryKey<World> worldRegistryKey) {
        public static final Codec<Location> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockPos.CODEC.fieldOf("position").forGetter(Location::pos),
                World.CODEC.fieldOf("world").forGetter(Location::worldRegistryKey)
        ).apply(instance, Location::new));

    }

}
