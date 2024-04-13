package net.insomniacs.nucleus.impl.splashTexts.types;

import com.mojang.serialization.Codec;
import net.insomniacs.nucleus.impl.splashTexts.util.TextProcessor;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public record SimpleSplashText(
        String string
) implements SplashText {

    public static final Codec<SimpleSplashText> SIMPLE_CODEC = Codec.STRING.xmap(SimpleSplashText::new, null);

    @Override
    public SplashText setStyle(Style style) {
        return new AdvancedSplashText(Text.literal(string)).setStyle(style);
    }

    @Override
    public SplashTextRenderer renderer() {
        return new SplashTextRenderer(this.string);
    }

    @Override
    public String toString() {
        return "SplashText["+this.string+"]";
    }

}
