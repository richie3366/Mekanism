package mekanism.common.item;

import java.util.List;

import mekanism.api.Coord4D;
import mekanism.api.EnumColor;
import mekanism.api.Pos3D;
import mekanism.common.entity.EntityBalloon;
import mekanism.common.util.MekanismUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBalloon extends ItemMekanism
{
	public ItemBalloon(int id)
	{
		super(id);
		setHasSubtypes(true);
	}

	public EnumColor getColor(ItemStack stack)
	{
		return EnumColor.DYES[stack.getItemDamage()];
	}

	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < EnumColor.DYES.length; i++)
		{
			EnumColor color = EnumColor.DYES[i];

			if(color != null)
			{
				ItemStack stack = new ItemStack(this);
				stack.setItemDamage(i);
				list.add(stack);
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(!world.isRemote)
		{
			Pos3D pos = new Pos3D();
			pos.zPos += 0.3;
			pos.xPos -= 0.4;
			pos.rotateYaw(entityplayer.renderYawOffset);
			pos.translate(new Pos3D(entityplayer));

			world.spawnEntityInWorld(new EntityBalloon(world, pos.xPos-0.5, pos.yPos-0.25, pos.zPos-0.5, getColor(itemstack)));
		}

		itemstack.stackSize--;

		return itemstack;
	}

	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		String color = getColor(stack).getDyedName();

		if(getColor(stack) == EnumColor.BLACK)
		{
			color = EnumColor.DARK_GREY + getColor(stack).getDyeName();
		}

		return color + " " + MekanismUtils.localize("tooltip.balloon");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(player.isSneaking())
		{
			AxisAlignedBB bound = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+3, z+1);

			List<EntityBalloon> balloonsNear = player.worldObj.getEntitiesWithinAABB(EntityBalloon.class, bound);

			if(balloonsNear.size() > 0)
			{
				return true;
			}

			Coord4D obj = new Coord4D(x, y, z, world.provider.dimensionId);

			if(Block.blocksList[obj.getBlockId(world)].isBlockReplaceable(world, x, y, z))
			{
				obj.yCoord--;
			}

			if(canReplace(world, obj.xCoord, obj.yCoord+1, obj.zCoord) && canReplace(world, obj.xCoord, obj.yCoord+2, obj.zCoord))
			{
				world.setBlockToAir(obj.xCoord, obj.yCoord+1, obj.zCoord);
				world.setBlockToAir(obj.xCoord, obj.yCoord+2, obj.zCoord);

				if(!world.isRemote)
				{
					world.spawnEntityInWorld(new EntityBalloon(world, obj, getColor(stack)));
					stack.stackSize--;
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
	{
		if(player.isSneaking())
		{
			if(!player.worldObj.isRemote)
			{
				AxisAlignedBB bound = AxisAlignedBB.getBoundingBox(entity.posX - 0.2, entity.posY - 0.5, entity.posZ - 0.2, entity.posX + 0.2, entity.posY + entity.ySize + 4, entity.posZ + 0.2);

				List<EntityBalloon> balloonsNear = player.worldObj.getEntitiesWithinAABB(EntityBalloon.class, bound);

				for(EntityBalloon balloon : balloonsNear)
				{
					if(balloon.latchedEntity == entity)
					{
						return true;
					}
				}

				player.worldObj.spawnEntityInWorld(new EntityBalloon(entity, getColor(stack)));
				stack.stackSize--;
			}

			return true;
		}

		return false;
	}

	private boolean canReplace(World world, int x, int y, int z)
	{
		return world.isAirBlock(x, y, z) || Block.blocksList[world.getBlockId(x, y, z)].isBlockReplaceable(world, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {}
}
