package net.insomniacs.nucleus.impl.splashTexts.types;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.insomniacs.nucleus.Nucleus;
import net.minecraft.text.Style;

import java.util.List;
import java.util.stream.Stream;

public record SplashTextGroup (
		Style defaultStyle,
		List<SplashText> texts
) {

	public static final Codec<SplashTextGroup> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Style.Codecs.CODEC.optionalFieldOf("default_style", Style.EMPTY).forGetter(SplashTextGroup::defaultStyle),
			SplashText.CODEC.listOf().fieldOf("entries").forGetter(SplashTextGroup::texts)
	).apply(instance, SplashTextGroup::new));

	public static SplashTextGroup fromJson(JsonObject array) {
		return CODEC.parse(JsonOps.INSTANCE, array).getOrThrow();
	}

	public Stream<SplashText> getTexts() {
		Stream<SplashText> result = texts.stream();
		if (defaultStyle != Style.EMPTY) result = result.map(text -> text.setStyle(defaultStyle));
		return result;
	}

}
