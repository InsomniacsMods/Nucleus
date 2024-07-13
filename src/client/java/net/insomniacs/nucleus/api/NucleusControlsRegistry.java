package net.insomniacs.nucleus.api;

import net.minecraft.client.option.SimpleOption;

import java.util.ArrayList;
import java.util.List;

public class NucleusControlsRegistry {

    private final List<SimpleOption<?>> options = new ArrayList<>();

    private static final NucleusControlsRegistry INSTANCE = new NucleusControlsRegistry();
    private NucleusControlsRegistry() {}

    public static void register(SimpleOption<?>... options) {
        for (SimpleOption<?> option : options) register(option);
    }
    public static void register(SimpleOption<?> option) {
        INSTANCE.options.add(option);
    }

    public static List<SimpleOption<?>> getOptions() {
        return INSTANCE.options;
    }

}
