package mekanism.client.gui;

import mekanism.common.tile.TileEntityFactory;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiRecipeType extends GuiElement
{
	TileEntityFactory tileEntity;

	public GuiRecipeType(IGuiWrapper gui, TileEntityFactory tile, ResourceLocation def)
	{
		super(MekanismUtils.getResource(ResourceType.GUI_ELEMENT, "GuiRecipeType.png"), gui, def);

		tileEntity = tile;
	}

	@Override
	public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight)
	{
		mc.renderEngine.bindTexture(RESOURCE);

		guiObj.drawTexturedRect(guiWidth + 176, guiHeight + 70, 0, 0, 26, 63);

		TileEntityFactory factory = tileEntity;
		int displayInt = factory.getScaledRecipeProgress(15);

		guiObj.drawTexturedRect(guiWidth + 181, guiHeight + 94, 26, 0, 10, displayInt);

		mc.renderEngine.bindTexture(defaultLocation);
	}

	@Override
	public void renderForeground(int xAxis, int yAxis) {}

	@Override
	public void preMouseClicked(int xAxis, int yAxis, int button)
	{
		if(button == 0)
		{
			if(xAxis >= 180 && xAxis <= 196 && yAxis >= 75 && yAxis <= 91)
			{
				offsetX(26);
			}
			else if(xAxis >= 180 && xAxis <= 196 && yAxis >= 112 && yAxis <= 128)
			{
				offsetX(26);
			}
		}
	}

	@Override
	public void mouseClicked(int xAxis, int yAxis, int button)
	{
		if(button == 0)
		{
			if(xAxis >= 180 && xAxis <= 196 && yAxis >= 75 && yAxis <= 91)
			{
				offsetX(-26);
			}
			else if(xAxis >= 180 && xAxis <= 196 && yAxis >= 112 && yAxis <= 128)
			{
				offsetX(-26);
			}
		}
	}
}
