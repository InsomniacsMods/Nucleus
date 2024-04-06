package net.insomniacs.nucleus.api.modreg.utils;

import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureMap;

public class BlockModelEntry {

	public Boolean generateBlock = false;
	public Model parent = null;
	public TextureMap textures = null;

	public Boolean generateItem = false;
	public String itemParentSuffix = "";

	public void generateBlock(Model model, TextureMap textures) {
		this.generateBlock = true;
		this.parent = model;
		this.textures = textures;
	}

	public void generateItem(String suffix) {
		this.generateItem = true;
		this.itemParentSuffix = suffix;
	}

}
