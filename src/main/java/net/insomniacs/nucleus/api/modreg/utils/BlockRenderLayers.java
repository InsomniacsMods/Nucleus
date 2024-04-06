package net.insomniacs.nucleus.api.modreg.utils;

public enum BlockRenderLayers {

	SOLID("solid"),
	CUTOUT("cutout"),
	CUTOUT_MIPMAP("cutout_mipped"),
	TRANSLUCENT("translucent");

	public final String layer;

	BlockRenderLayers(String layer) {
		this.layer = layer;
	}

}
