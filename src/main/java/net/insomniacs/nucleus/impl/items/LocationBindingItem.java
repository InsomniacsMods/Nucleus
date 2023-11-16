package net.insomniacs.nucleus.impl.items;

import net.insomniacs.nucleus.impl.misc.Location;
import net.minecraft.client.item.TooltipContext;
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
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class LocationBindingItem extends Item {

    public static final String BIND_DATA = "location";
    public static final String IS_BOUND_KEY = "bound";

    public LocationBindingItem(Item.Settings settings) {
        super(settings);
    }

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
        if(!nbt.contains(BIND_DATA)) return Text.empty();

        Location data = Location.CODEC.parse(NbtOps.INSTANCE, nbt.get(BIND_DATA)).get().orThrow();
        BlockPos pos = data.pos();
        Text dimension = Text.translatable(data.worldRegistryKey().getValue().toTranslationKey("dimension"));

        return Text.translatable("item.nucleus.location_binding_item.location",
                dimension, pos.getX(), pos.getY(), pos.getZ()
        ).formatted(Formatting.GRAY);
    }

}
