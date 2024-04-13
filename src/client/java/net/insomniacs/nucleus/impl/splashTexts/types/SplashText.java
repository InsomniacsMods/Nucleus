package net.insomniacs.nucleus.impl.splashTexts.types;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.text.Style;
import net.minecraft.util.dynamic.Codecs;

import static net.insomniacs.nucleus.impl.splashTexts.types.AdvancedSplashText.ADVANCED_CODEC;
import static net.insomniacs.nucleus.impl.splashTexts.types.SimpleSplashText.SIMPLE_CODEC;

public interface SplashText {

    Codec<SplashText> CODEC = Codecs.either(ADVANCED_CODEC, SIMPLE_CODEC).xmap(Either::orThrow, null);


    default int getWeight() { return 1; }
    default boolean validate() { return true; }

    SplashTextRenderer renderer();
    SplashText setStyle(Style style);

}
