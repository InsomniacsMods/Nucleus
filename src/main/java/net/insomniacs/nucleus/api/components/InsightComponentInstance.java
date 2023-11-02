package net.insomniacs.nucleus.api.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class InsightComponentInstance implements InsightComponent, AutoSyncedComponent {

    private static final String INSIGHT_KEY = "insight";
    private byte insightLevel;
    private final PlayerEntity provider;

    public InsightComponentInstance(PlayerEntity provider) {
        this.provider = provider;
    }

    @Override
    public byte getInsightLevel() {
        return insightLevel;
    }

    @Override
    public void setInsightLevel(byte level) {
        this.insightLevel = level;
        ComponentKeys.INSIGHT.sync(provider);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        insightLevel = tag.getByte(INSIGHT_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putByte(INSIGHT_KEY, insightLevel);
    }
}
