package net.insomniacs.nucleus.impl.items;

import net.insomniacs.nucleus.impl.misc.BundleItemSoundGroup;
import net.insomniacs.nucleus.impl.misc.NucleusSoundEvents;
import net.insomniacs.nucleus.impl.misc.NucleusTags;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class CustomBundleItem extends BundleItem {

    public BundleItemSoundGroup soundGroup;
    public Integer maxStorage;

    public static Integer getMaxStorage(ItemStack stack) {
        NbtCompound compound = stack.getOrCreateNbt();
        if (!compound.contains("max_storage")) {
            CustomBundleItem item = (CustomBundleItem) stack.getItem();
            return item.maxStorage;
        }
        return compound.getInt("max_storage");
    }

    public Function<ItemStack, Integer> itemOccupancy;
    public Function<ItemStack, Integer> DEFAULT_ITEM_OCCUPANCY = stack -> (64 / stack.getMaxCount()) * stack.getCount();

    public int getItemOccupancy(ItemStack stack) {
        return itemOccupancy.apply(stack);
    }


    public CustomBundleItem(Settings settings, @Nullable BundleItemSoundGroup soundGroup, @Nullable Integer maxStorage, @Nullable Function<ItemStack, Integer> itemOccupancy) {
        super(settings);
        this.soundGroup = soundGroup == null ? NucleusSoundEvents.BUNDLE_ITEM : soundGroup;
        this.maxStorage = maxStorage == null ? 64 : maxStorage;
        this.itemOccupancy = itemOccupancy == null ? DEFAULT_ITEM_OCCUPANCY : itemOccupancy;
    }

    public static float getAmountFilled(ItemStack stack) {
        return (float)getBundleOccupancy(stack) / getMaxStorage(stack);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getStackInHand(hand);

        if (!dropAllBundledItems(stack, player)) return TypedActionResult.fail(stack);

        playBundleSound(player, soundGroup.dropSound());
        player.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(stack, world.isClient());
    }

    public boolean dropAllBundledItems(ItemStack stack, PlayerEntity player) {

        NbtCompound nbt = stack.getOrCreateNbt();

        if (!nbt.contains("Items")) return false;

        if (player instanceof ServerPlayerEntity) {

            NbtList list = nbt.getList("Items", 10);

            for(int i = 0; i < list.size(); ++i) {
                NbtCompound compoundList = list.getCompound(i);
                player.dropItem(ItemStack.fromNbt(compoundList), true);
            }
        }
        stack.removeSubNbt("Items");
        return true;
    }

    @Override
    public boolean onClicked(ItemStack bundleStack, ItemStack clickedStack, Slot slot, ClickType click, PlayerEntity player, StackReference cursorStackReference) {

        if (click != ClickType.RIGHT) return false;
        if (!slot.canTakePartial(player)) return false;

        if (clickedStack.isEmpty()) {
            removeFirstStack(bundleStack).ifPresent((itemStack) -> {
                playBundleSound(player, soundGroup.removeSound());
                cursorStackReference.set(itemStack);
            });
        } else {
            int i = addStack(bundleStack, clickedStack);
            if (i > 0) {
                playBundleSound(player, soundGroup.addSound());
                clickedStack.decrement(i);
            }
        }
        return true;
    }

    @Override
    public boolean onStackClicked(ItemStack bundleStack, Slot slot, ClickType clickType, PlayerEntity player) {

        if (clickType != ClickType.RIGHT) return false;

        ItemStack clickedStack = slot.getStack();

        if (clickedStack.isEmpty()) {
            playBundleSound(player, soundGroup.removeSound());
            removeFirstStack(bundleStack).ifPresent((removedStack) -> addStack(bundleStack, slot.insertStack(removedStack)));
        } else if (clickedStack.getItem().canBeNested()) {
            int i = addStack(bundleStack, slot.takeStackRange(clickedStack.getCount(), remainingStackSize(bundleStack, clickedStack), player));
            if (i > 0) {
                playBundleSound(player, soundGroup.addSound());
            }
        }
        return true;
    }

    public Optional<ItemStack> removeFirstStack(ItemStack stack) {

        NbtCompound compound = stack.getOrCreateNbt();
        if (!compound.contains("Items")) return Optional.empty();

        NbtList list = compound.getList("Items", 10);
        if (list.isEmpty()) return Optional.empty();

        ItemStack newStack = ItemStack.fromNbt(list.getCompound(0));
        list.remove(0);
        if (list.isEmpty()) stack.removeSubNbt("Items");

        return Optional.of(newStack);
    }

    public int addStack(ItemStack bundle, ItemStack stack) {

        if (stack.isEmpty()) return 0;
        if (!stack.getItem().canBeNested()) return 0;
        if (stack.isIn(NucleusTags.CANNOT_BE_INSERTED_INTO_BUNDLES)) return 0;

        NbtCompound compound = bundle.getOrCreateNbt();
        if (!compound.contains("Items")) compound.put("Items", new NbtList());

        int stackSize = Math.min(stack.getCount(), remainingStackSize(bundle, stack));
        if (stackSize == 0) return stackSize;

        return addStack(bundle, stack, stackSize);
    }

    public int addStack(ItemStack bundle, ItemStack stack, int stackSize) {

        NbtCompound compound = bundle.getOrCreateNbt();
        NbtList list = compound.getList("Items", 10);
        Optional<NbtCompound> optional = canMergeStack(stack, list);

        ItemStack newStack;
        NbtCompound newCompound;

        if (optional.isPresent()) {
            newCompound = optional.get();
            newStack = ItemStack.fromNbt(newCompound);
            newStack.increment(stackSize);
            newStack.writeNbt(newCompound);
            list.remove(newCompound);
        } else {
            newStack = stack.copyWithCount(stackSize);
            newCompound = new NbtCompound();
            newStack.writeNbt(newCompound);
        }

        list.add(0, newCompound);
        return stackSize;
    }

    public int remainingStackSize(ItemStack bundle, ItemStack stack) {
        int currentOccupancy = getBundleOccupancy(bundle);
        ItemStack tempItem = stack.copyWithCount(this.getMaxStorage(bundle) - currentOccupancy);
        return getItemOccupancy(tempItem);
    }

    public static Optional<NbtCompound> canMergeStack(ItemStack stack, NbtList items) {

        if (stack.isOf(Items.BUNDLE)) return Optional.empty();

        Stream<NbtElement> stream = items.stream();
        Objects.requireNonNull(NbtCompound.class);
        stream = stream.filter(NbtCompound.class::isInstance);
        Objects.requireNonNull(NbtCompound.class);

        return stream.map(NbtCompound.class::cast).filter((item) -> ItemStack.canCombine(ItemStack.fromNbt(item), stack)).findFirst();
    }

    public static int getBundleOccupancy(ItemStack stack) {
        CustomBundleItem item = (CustomBundleItem)stack.getItem();
        return getBundleStacks(stack).mapToInt(item::getItemOccupancy).sum();
    }

    public static Stream<ItemStack> getBundleStacks(ItemStack stack) {

        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return Stream.empty();

        NbtList nbtList = nbt.getList("Items", 10);
        Stream<NbtElement> stream = nbtList.stream();
        return stream.map(NbtCompound.class::cast).map(ItemStack::fromNbt);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.minecraft.bundle.fullness", getBundleOccupancy(stack), getMaxStorage(stack)).formatted(Formatting.GRAY));
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.min(1 + 12 * getBundleOccupancy(stack) / getMaxStorage(stack), 13);
    }

    @Override
    public boolean canBeNested() {
        return false;
    }

//    TODO implement a new TooltipComponent that properly displays the max storage size
//    @Override
//    public Optional<TooltipData> getTooltipData(ItemStack stack) {
//    }

    public void playBundleSound(Entity entity, SoundEvent sound) {
        entity.playSound(sound, soundGroup.volume(), soundGroup.pitch() + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

}