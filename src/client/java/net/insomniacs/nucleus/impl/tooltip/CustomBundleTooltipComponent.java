package net.insomniacs.nucleus.impl.tooltip;

import net.insomniacs.nucleus.api.components.BundleComponent;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;

public class CustomBundleTooltipComponent implements TooltipComponent {

	public static final Identifier BACKGROUND_TEXTURE = new Identifier("container/bundle/background");

	public List<ItemStack> contents;
	public int size;
	public boolean fullCapacity;

	public CustomBundleTooltipComponent(BundleComponent component) {
		this.contents = component.contents();
		this.size = contents.size();
		this.fullCapacity = component.occupancy() == component.capacity();
	}

	@Override
	public int getHeight() {
		return this.getRowsHeight() + 4;
	}

	@Override
	public int getWidth(TextRenderer textRenderer) {
		return this.getColumnsWidth();
	}

	public int getColumnsWidth() {
		return this.getColumns() * 18 + 2;
	}

	public int getRowsHeight() {
		return this.getRows() * 20 + 2;
	}

	private int getColumns() {
		return Math.max(2, (int)Math.ceil(Math.sqrt(size + 1.0)));
	}

	private int getRows() {
		return (int)Math.ceil((size + 1.0) / (double)this.getColumns());
	}

	@Override
	public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
		int columns = this.getColumns();
		int rows = this.getRows();
		context.drawGuiTexture(BACKGROUND_TEXTURE, x, y, this.getColumnsWidth(), this.getRowsHeight());
		boolean block = size > 1;

		int i = 0;
		for(int r = 0; r < rows; ++r) {
			for(int c = 0; c < columns; ++c) {
				int c1 = x + c * 18 + 1;
				int r1 = y + r * 20 + 1;
				this.drawSlot(c1, r1, i++, block, context, textRenderer);
			}
		}

	}

	public void drawSlot(int x, int y, int index, boolean shouldBlock, DrawContext context, TextRenderer textRenderer) {
		// TODO make the BLOCKED sprite only render while at full capacity
		if (index >= size) {
			this.draw(context, x, y, shouldBlock ? SlotSprite.BLOCKED : SlotSprite.SLOT);
			return;
		}
		ItemStack stack = contents.get(index);
		this.draw(context, x, y, SlotSprite.SLOT);
		context.drawItem(stack, x + 1, y + 1, index);
		context.drawItemInSlot(textRenderer, stack, x + 1, y + 1);
		if (index == 0) HandledScreen.drawSlotHighlight(context, x + 1, y + 1, 0);
	}

	public void draw(DrawContext context, int x, int y, SlotSprite sprite) {
		context.drawGuiTexture(sprite.texture, x, y, 0, 18, 20);
	}

	public enum SlotSprite {
		BLOCKED("container/bundle/blocked_slot"),
		SLOT("container/bundle/slot");

		public final Identifier texture;

		SlotSprite(String path) {
			this.texture = new Identifier(path);
		}
	}

}
