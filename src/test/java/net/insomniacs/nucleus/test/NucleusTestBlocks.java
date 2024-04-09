package net.insomniacs.nucleus.test;

import net.insomniacs.nucleus.api.modreg.utils.CustomRegistrySupplier;
import net.insomniacs.nucleus.api.modreg.entries.BlockEntry;
import net.insomniacs.nucleus.api.modreg.entries.ItemEntry;
import net.insomniacs.nucleus.api.modreg.entries.PotionEntry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.PowderSnowBucketItem;
import net.minecraft.potion.Potion;
import net.minecraft.sound.SoundEvents;

import static net.insomniacs.nucleus.test.NucleusTest.REGISTRY;

public class NucleusTestBlocks {

    public static final BlockEntry BASIC = REGISTRY.block("basic_block", Block::new)
            .dropsSelf()
            .model()
            .translate()
            .defaultItem()
            .register();

    public static final BlockEntry STONE = REGISTRY.block("basic_stone", Block::new)
            .dropsSelf()
            .requiresPickaxe()
            .model()
            .translate()
            .defaultItem()
            .register();

    public static final BlockEntry STUPID = REGISTRY.block("stupid", Block::new)
            .drops(Items.ACACIA_PLANKS)
            .translate()
            .defaultItem(BlockItem::new)
            .modifyItem(item -> item
                    .creativeTab(ItemGroups.TOOLS)
                    .unstackable())
            .register();

    public static final BlockEntry LIGHT = REGISTRY.block("light", Block::new)
            .translate()
            .defaultItem(BlockItem::new)
            .luminance(15)
            .register();

    public static final BlockEntry DYNAMIC_LIGHT = REGISTRY.block("dynamic_light", RedstoneLampBlock::new)
            .translate()
            .defaultItem("basic_lamp", BlockItem::new)
            .luminance(Blocks.createLightLevelFromLitBlockState(15))
            .register();

    public static final BlockEntry FAKE_LIQUID = REGISTRY.block("fake_liquid", Block::new)
            .translate()
            .defaultItem("bucket_of_fake_liquid", (block, settings) -> new PowderSnowBucketItem(block, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, settings))
            .modifyItem(ItemEntry.Builder::unstackable)
            .register();


//    public static final CustomRegistrySupplier<PotionEntry.Builder, Potion> POTION_REGISTRY =
//            REGISTRY.custom(PotionEntry.Builder::new);

//    public static final Potion test = POTION_REGISTRY.create("test").register().value();



    public static void init() {

    }

}
