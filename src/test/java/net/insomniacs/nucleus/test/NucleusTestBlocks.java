package net.insomniacs.nucleus.test;

import net.insomniacs.nucleus.api.modreg.CustomRegistrySupplier;
import net.insomniacs.nucleus.api.modreg.provider.potion.PotionBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;

import static net.insomniacs.nucleus.test.NucleusTest.REGISTRY;

public class NucleusTestBlocks {

    public static final Block BASIC = REGISTRY.block("basic_item", Block::new).register().value();

    public static final Block LIGHT = REGISTRY.block("light", Block::new)
            .luminance(15)
            .register().value();

    public static final Block DYNAMIC_LIGHT = REGISTRY.block("dynamic_light", RedstoneLampBlock::new)
            .luminance(Blocks.createLightLevelFromLitBlockState(15))
            .register().value();



    public static final CustomRegistrySupplier<PotionBuilder, StatusEffectInstance, Potion> POTION_REGISTRY =
            REGISTRY.custom(PotionBuilder::new);

    public static final Potion test = POTION_REGISTRY.create("test", Potion::new).register().value();


    public static void init() {

    }

}
