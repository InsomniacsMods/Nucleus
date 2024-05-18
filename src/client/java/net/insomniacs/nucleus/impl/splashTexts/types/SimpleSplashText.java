package net.insomniacs.nucleus.impl.splashTexts.types;

import com.mojang.serialization.Codec;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class SimpleSplashText implements SplashText {

    private final String text;

    public SimpleSplashText(String text) {
        this.text = text;
    }

    public static final Codec<SimpleSplashText> SIMPLE_CODEC = Codec.STRING.xmap(SimpleSplashText::new, null);

    @Override
    public int getWeight() {
        return 1;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public SplashTextRenderer renderer() {
        return new SplashTextRenderer(this.text);
    }

    @Override
    public SplashText setStyle(Style style) {
        return new AdvancedSplashText(Text.literal(text)).setStyle(style);
    }

    @Override
    public String toString() {
        return "SimpleSplashText["+this.text+"]";
    }

}
