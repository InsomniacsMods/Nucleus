package net.insomniacs.nucleus.impl.splashTexts.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TextProcessor {

    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    public static final Map<String, Supplier<String>> FORMATS = Map.of(
            "%USER%", () -> CLIENT.getGameProfile().getName().toUpperCase(),
            "%user%", () -> CLIENT.getGameProfile().getName()
    );

    public static String format(String string) {
        if (!string.contains("%")) return string;

        for (String find : FORMATS.keySet()) {
            Supplier<String> replace = FORMATS.get(find);
            string = string.replace(find, replace.get());
        }
        return string;
    }

    public static MutableText process(Text text) {
        if (!text.getString().contains("%")) return (MutableText)text;

        MutableText result = Text
                .literal(format(text.getString()))
                .setStyle(text.getStyle());
        text.getSiblings().forEach(child -> result.append(TextProcessor.process(child)));
        return result;
    }

}
