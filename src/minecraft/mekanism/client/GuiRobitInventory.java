package mekanism.client;

import mekanism.common.ContainerRobitInventory;
import mekanism.common.EntityRobit;
import mekanism.common.Mekanism;
import mekanism.common.PacketHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiRobitInventory extends GuiContainer
{
	public EntityRobit robit;
	
	public GuiRobitInventory(InventoryPlayer inventory, EntityRobit entity)
	{
		super(new ContainerRobitInventory(inventory, entity));
		xSize += 25;
		robit = entity;
	}
	
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
    	fontRenderer.drawString("Robit Inventory", 8, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, ySize - 96 + 3, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/mods/mekanism/gui/GuiRobitInventory.png");
        int guiWidth = (width - xSize) / 2;
        int guiHeight = (height - ySize) / 2;
        drawTexturedModalRect(guiWidth, guiHeight, 0, 0, xSize, ySize);
        
		int xAxis = (mouseX - (width - xSize) / 2);
		int yAxis = (mouseY - (height - ySize) / 2);
        
		if(xAxis >= 179 && xAxis <= 197 && yAxis >= 10 && yAxis <= 28)
		{
			drawTexturedModalRect(guiWidth + 179, guiHeight + 10, 176 + 25, 0, 18, 18);
		}
		else {
			drawTexturedModalRect(guiWidth + 179, guiHeight + 10, 176 + 25, 18, 18, 18);
		}
		
		if(xAxis >= 179 && xAxis <= 197 && yAxis >= 30 && yAxis <= 48)
		{
			drawTexturedModalRect(guiWidth + 179, guiHeight + 30, 176 + 25, 36, 18, 18);
		}
		else {
			drawTexturedModalRect(guiWidth + 179, guiHeight + 30, 176 + 25, 54, 18, 18);
		}
		
		if(xAxis >= 179 && xAxis <= 197 && yAxis >= 50 && yAxis <= 68)
		{
			drawTexturedModalRect(guiWidth + 179, guiHeight + 50, 176 + 25, 72, 18, 18);
		}
		else {
			drawTexturedModalRect(guiWidth + 179, guiHeight + 50, 176 + 25, 90, 18, 18);
		}
    }
    
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button)
	{
		super.mouseClicked(mouseX, mouseY, button);
		
		if(button == 0)
		{
			int xAxis = (mouseX - (width - xSize) / 2);
			int yAxis = (mouseY - (height - ySize) / 2);
			
			if(xAxis >= 179 && xAxis <= 197 && yAxis >= 10 && yAxis <= 28)
			{
				mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
				PacketHandler.sendRobitGui(0, robit.entityId);
				mc.thePlayer.openGui(Mekanism.instance, 21, mc.theWorld, robit.entityId, 0, 0);
			}
			else if(xAxis >= 179 && xAxis <= 197 && yAxis >= 30 && yAxis <= 48)
			{
				mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
				PacketHandler.sendRobitGui(1, robit.entityId);
				mc.thePlayer.openGui(Mekanism.instance, 22, mc.theWorld, robit.entityId, 0, 0);
			}
			else if(xAxis >= 179 && xAxis <= 197 && yAxis >= 50 && yAxis <= 68)
			{
				mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			}
		}
	}
}