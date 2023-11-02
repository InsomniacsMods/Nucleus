package net.insomniacs.nucleus.api.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface InsightComponent extends ComponentV3 {

    byte getInsightLevel();
    void setInsightLevel(byte level);

}
