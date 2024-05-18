package net.insomniacs.nucleus.impl.splashTexts.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.utils.DateUtils;
import net.insomniacs.nucleus.impl.splashTexts.util.AdvancedSplashTextRenderer;
import net.insomniacs.nucleus.impl.splashTexts.util.TextProcessor;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.dynamic.Codecs;

import java.time.LocalDate;
import java.util.function.UnaryOperator;

public class AdvancedSplashText implements SplashText {

    private MutableText text;
    private final int weight;
    private final String mod;
    private final LocalDate date;

    public AdvancedSplashText(
            Text text,
            Integer weight,
            String mod,
            LocalDate date
    ) {
        this.text = TextProcessor.process(text);
        this.weight = weight;
        this.mod = mod;
        this.date = date;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean validate() {
        if (!DateUtils.isToday(this.date)) return false;
        if (!Nucleus.isModLoaded(this.mod)) return false;
        return true;
    }

    public AdvancedSplashText(MutableText text) {
        this(text, 1, "minecraft", DateUtils.today());
    }

    public void modifyText(UnaryOperator<MutableText> operator) {
        this.text = operator.apply(this.text);
    }

    public static final Codec<AdvancedSplashText> ADVANCED_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TextCodecs.CODEC.fieldOf("text").forGetter(null),
            Codecs.rangedInt(1, 128).optionalFieldOf("weight", 1).forGetter(null),
            Codec.STRING.optionalFieldOf("mod", "minecraft").forGetter(null),
            DateUtils.CODEC.optionalFieldOf("date", DateUtils.today()).forGetter(null)
    ).apply(instance, AdvancedSplashText::new));

    @Override
    public SplashTextRenderer renderer() {
        return new AdvancedSplashTextRenderer(this.text);
    }

    @Override
    public SplashText setStyle(Style style) {
        modifyText(text -> text.setStyle(style));
        return this;
    }

    @Override
    public String toString() {
        return "AdvancedSplashText["+this.text.getString()+"]";
    }

}
