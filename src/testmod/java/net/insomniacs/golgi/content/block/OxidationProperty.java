package net.insomniacs.golgi.content.block;

import net.minecraft.block.Oxidizable;
import net.minecraft.state.property.Property;

import java.util.*;

public class OxidationProperty extends Property<Oxidizable.OxidationLevel> {

	private static final List<Oxidizable.OxidationLevel> VALUES = Arrays.asList(Oxidizable.OxidationLevel.values());

	public OxidationProperty(String name) {
		super(name, Oxidizable.OxidationLevel.class);
	}

	public static OxidationProperty of(String name) {
		return new OxidationProperty(name);
	}

	@Override
	public Collection<Oxidizable.OxidationLevel> getValues() {
		return VALUES;
	}

	@Override
	public String name(Oxidizable.OxidationLevel value) {
		return "oxidation";
	}

	@SuppressWarnings("OptionalOfNullableMisuse")
	@Override
	public Optional<Oxidizable.OxidationLevel> parse(String name) {
		return Optional.ofNullable(Oxidizable.OxidationLevel.valueOf(name));
	}

}
