package eggdropsoap.milestones;

import java.lang.reflect.Field;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class MilestoneBlock extends Block {

    /** The block that is used as model for the milestone. */
    protected final Block modelBlock;
    private final int metadata;
    public static final float truncatedPyramidHeight = 0.7F;

	
	public MilestoneBlock (int id, Block modelBlock, int metadata) {
		super(id, modelBlock.blockIndexInTexture, modelBlock.blockMaterial);
        this.modelBlock = modelBlock;
        this.metadata = metadata;
//      this.setResistance(modelBlock.blockResistance / 3.0F);
        this.setStepSound(modelBlock.stepSound);
//        this.setLightOpacity(255);
        this.setCreativeTab(CreativeTabs.tabBlock);
        
//        float bh = getProtectedFloat(Block.class, "field_71989_cb")	// blockHardness in MC 1.4.7
        float blockHardness = getProtectedBlockFloat(Block.class, modelBlock, "blockHardness");
        this.setHardness(blockHardness);
        

	}
	
	private float getProtectedBlockFloat(Class c, Block modelBlock, String fieldName){
		float bh = -1.0F;
		
		Field f;
		try {
			f = c.getDeclaredField(fieldName);
			f.setAccessible(true);

			try {
				bh = f.getFloat(modelBlock);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        System.out.println("Accessing: Block " + modelBlock.getBlockName() + ", field \"" + fieldName + "\", value : " + bh);

		return bh;
	}

/*	@Override
	public String getTextureFile () {
		return CommonProxy.BLOCK_PNG;
	}*/

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
    public int getRenderType()
    {
        return MilestonesBase.truncatedPyramidRenderId;
    }
	
    @SideOnly(Side.CLIENT)
    /**
     * Goes straight to getLightBrightnessForSkyBlocks for Blocks, does some fancy computing for Fluids
     */
    public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return this.modelBlock.getMixedBrightnessForBlock(par1IBlockAccess, par2, par3, par4);
    }

    @SideOnly(Side.CLIENT)
    /**
     * How bright to render this block based on the light its receiving. Args: iBlockAccess, x, y, z
     */
    public float getBlockBrightness(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return this.modelBlock.getBlockBrightness(par1IBlockAccess, par2, par3, par4);
    }
    
    public int getBlockTextureFromSide(int side)
    {
        return this.modelBlock.getBlockTextureFromSide(side);
    }

}
