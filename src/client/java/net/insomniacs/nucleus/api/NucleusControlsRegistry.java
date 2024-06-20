package net.insomniacs.nucleus.api;

import net.minecraft.client.option.SimpleOption;

import java.util.ArrayList;
import java.util.List;

public class NucleusControlsRegistry {

    private final List<SimpleOption<?>> options;

    private static final NucleusControlsRegistry INSTANCE = new NucleusControlsRegistry();

    private NucleusControlsRegistry() {
        this.options = new ArrayList<>();
    }


    public static void addOptions(SimpleOption<?>... options) {
        for (SimpleOption<?> option : options) addOption(option);
    }

    public static void addOption(SimpleOption<?> option) {
        INSTANCE.options.add(option);
    }

    public static List<SimpleOption<?>> getOptions() {
        return INSTANCE.options;
    }

    public static void clear() {
        INSTANCE.options.clear();
    }

}
