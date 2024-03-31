package net.insomniacs.nucleus.datagen.providers.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class BaseModelProvider<G> {

    public interface BaseModelConsumer<G> {
        void accept(G generator);
    }

    public final List<BaseModelConsumer<G>> consumers;

    public BaseModelProvider() {
        this.consumers = new ArrayList<>();
    }

    public <O> void addAll(O[] objects, BiConsumer<G, O> biConsumer) {
        consumers.add(generator -> Arrays.stream(objects).forEach(object -> biConsumer.accept(generator, object)));
    }

    @SuppressWarnings("unused")
    @SafeVarargs
    public final void addCustom(BaseModelConsumer<G>... constructors) {
        addAll(constructors, (generator, constructor) -> constructor.accept(generator));
    }

}
