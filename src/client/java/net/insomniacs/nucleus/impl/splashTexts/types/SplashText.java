package net.insomniacs.nucleus.impl.splashTexts.types;

import com.mojang.serialization.Codec;
import net.insomniacs.nucleus.api.utils.CodecUtils;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.text.Style;

import static net.insomniacs.nucleus.impl.splashTexts.types.AdvancedSplashText.ADVANCED_CODEC;
import static net.insomniacs.nucleus.impl.splashTexts.types.SimpleSplashText.SIMPLE_CODEC;

public interface SplashText {

    Codec<SplashText> CODEC = CodecUtils.merge(ADVANCED_CODEC, SIMPLE_CODEC);

    int getWeight();
    boolean validate();
    SplashTextRenderer renderer();
    SplashText setStyle(Style style);

}
