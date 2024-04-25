package net.insomniacs.nucleus.api.tooltipLoader;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.insomniacs.nucleus.Nucleus;
import net.insomniacs.nucleus.api.dataLoader.SimpleFileLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModTooltipLoader implements SimpleFileLoader {

	public static final ModTooltipLoader INSTANCE = new ModTooltipLoader();

	@Override
	public Identifier getFabricId() {
		return Nucleus.of("mod_tooltips");
	}

	private final Map<String, Text> modTooltips;
	private ModTooltipLoader() {
		this.modTooltips = new HashMap<>();
	}

	public Text getTooltip(String modId) {
		return modTooltips.getOrDefault(modId, Text.empty());
	}

	public void addTooltip(String modId, Text tooltip) {
		modTooltips.put(modId, tooltip);
	}

	@Override
	public void init(DataFileLoader loader, ResourceManager manager) {
		loader.json().findAllMatching("texts/tooltip.json", this::readTooltipFile);
	}

	public void readTooltipFile(Identifier id, JsonElement data) {
		System.out.println("AAAAAAAAA");
		System.out.println(id);
		System.out.println(data);
		Text text = TextCodecs.CODEC.parse(JsonOps.INSTANCE, data.getAsJsonObject()).getOrThrow();
		System.out.println(text);
		if (!text.getString().isEmpty()) addTooltip(id.getNamespace(), text);
	}

}
