package net.insomniacs.nucleus.api.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.insomniacs.nucleus.api.utils.NucleusMathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * The sanity component logic, this is integrated directly into nucleus to make me have less hassle
 * @author dragoncommands
 */
public class SanityComponentInstance implements SanityComponent, AutoSyncedComponent {

    private static final String SANITY_KEY = "sanity";
    public static final int MAX_SANITY = 100;
    private int sanity;
    private final PlayerEntity provider;

    public SanityComponentInstance(PlayerEntity provider) {
        this.provider = provider;
        this.sanity = MAX_SANITY;
    }

    @Override
    public void setSanity(int sanity) {
        this.sanity = NucleusMathUtils.clamp(sanity, 0, MAX_SANITY);
        ComponentKeys.SANITY.sync(this.provider);
    }

    @Override
    public int getSanity() {
        return sanity;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        sanity = tag.getInt("sanity");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("sanity", sanity);
    }

}
