package net.insomniacs.nucleus.impl.components;

import net.insomniacs.nucleus.api.components.DataComponent;

import java.util.List;

public interface ComponentContainer {

    void addComponent(DataComponent dataComponent);
    List<DataComponent> getComponents();

}
