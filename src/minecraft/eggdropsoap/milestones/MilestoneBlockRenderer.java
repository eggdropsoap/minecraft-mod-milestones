package eggdropsoap.milestones;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class MilestoneBlockRenderer implements ISimpleBlockRenderingHandler
{
	public enum Side { BOTTOM, TOP, EAST, WEST, NORTH, SOUTH }
	
    public void renderInventoryBlock(Block block, int metadata, int renderId, RenderBlocks renderer)
    {
    	Tessellator tessellator = Tessellator.instance;
    	float x = 0.0F;
    	float y = (1.0F - MilestoneBlock.truncatedPyramidHeight) / 2;	// vertical centering in inventory slot
    	float z = 0.0F;
    	
    	tessellator.startDrawingQuads();
    	tessellator.setNormal(0.0F, -1.0F, 0.0F);
    	this.renderFace(block, x, y, z, Side.BOTTOM, renderId); 	
    	tessellator.draw();    
    	
    	tessellator.startDrawingQuads();
       	tessellator.setNormal(0.0F, 1.0F, 0.0F);
        this.renderFace(block, x, y, z, Side.TOP, renderId);
    	tessellator.draw();    
        
    	tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
    	this.renderFace(block, x, y, z, Side.WEST, renderId);
    	tessellator.draw();    

    	tessellator.startDrawingQuads();
    	tessellator.setNormal(0.0F, 0.0F, 1.0F);
    	this.renderFace(block, x, y, z, Side.EAST, renderId);
    	tessellator.draw();    
    	
    	tessellator.startDrawingQuads();
    	tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        this.renderFace(block, x, y, z, Side.SOUTH, renderId);
    	tessellator.draw();    
    	
    	tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        this.renderFace(block, x, y, z, Side.NORTH, renderId);
    	tessellator.draw();    
    	
    	
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int renderId, RenderBlocks renderer)
    {
    	Tessellator tessellator = Tessellator.instance;

    	float height = MilestoneBlock.truncatedPyramidHeight;
//    	float topHalfWidth = 0.375F;
//    	float bottomHalfWidth = 0.5F;
 	   	
//    	int textureIndex = block.getBlockTextureFromSide(0);
    	float color = 1.0F;
//        int textureCornerPixelX = (textureIndex & 15) << 4;
//        int textureCornerPixelY = textureIndex & 240;
//        double minU = (double)((float)textureCornerPixelX / 256.0F);
//        double maxU = (double)(((float)textureCornerPixelX + 15.99F) / 256.0F);
//        double minV = (double)((float)textureCornerPixelY / 256.0F);
//        double maxV = (double)(((float)textureCornerPixelY + 15.99F) / 256.0F);
//        double topUAdjust = (double)((0.5F - topHalfWidth) / 16.0F);	// offset into texture to map onto trapezoid correctly
    	
        tessellator.setColorOpaque_F(color, color, color);
        
        // sides
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        this.renderFace(block, x, y, z, Side.WEST, renderId);
        this.renderFace(block, x, y, z, Side.NORTH, renderId);
        this.renderFace(block, x, y, z, Side.SOUTH, renderId);
    	this.renderFace(block, x, y, z, Side.EAST, renderId);

    	//top has to account for shadowing
    	int brightnessAbove = block.getMixedBrightnessForBlock(world, x, y + 1, z);
    	int brightnessHere = block.getMixedBrightnessForBlock(world, x, y, z);  	
        tessellator.setBrightness(brightnessAbove < brightnessHere ? (brightnessAbove + brightnessHere) / 2 : brightnessHere);
        this.renderFace(block, x, y, z, Side.TOP, renderId);

        // bottom slab
        float slabHeight = 1.0F / 16.0F;
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, slabHeight, 1.0F);
    	renderer.updateCustomBlockBounds(block);
    	renderer.renderStandardBlock(block, x, y, z);
    	
    	block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, height, 1.0F);
//    	renderer.renderStandardBlock(block, x, y, z);
//    	renderer.overrideBlockTexture = -1;

//    	block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    	return true;
    }

    private void renderFace(Block block, float x, float y, float z, Side side, int renderId) {
    	Tessellator tessellator = Tessellator.instance;

    	float height = MilestoneBlock.truncatedPyramidHeight;
    	float topHalfWidth = 0.375F;
//    	float bottomHalfWidth = 0.5F;
 	   	
    	int textureIndex = block.getBlockTextureFromSide(side.ordinal());
        int textureCornerPixelX = (textureIndex & 15) << 4;
        int textureCornerPixelY = textureIndex & 240;
        double minU = (double)((float)textureCornerPixelX / 256.0F);
        double maxU = (double)(((float)textureCornerPixelX + 15.99F) / 256.0F);
        double minV = (double)((float)textureCornerPixelY / 256.0F);
        double maxV = (double)(((float)textureCornerPixelY + 15.99F) / 256.0F);
        double topUAdjust = (double)((0.5F - topHalfWidth) / 16.0F);	// offset into texture to map onto trapezoid correctly
  	
    	switch (side) {
    	case WEST:
    		tessellator.addVertexWithUV(x + 0.5F - topHalfWidth, y + height, z + 0.5F - topHalfWidth, minU + topUAdjust, minV);
    		tessellator.addVertexWithUV(x + 0.0F, y + 0.0F, z + 0.0F, minU, maxV);
    		tessellator.addVertexWithUV(x + 0.0F, y + 0.0F, z + 1.0F, maxU, maxV);
    		tessellator.addVertexWithUV(x + 0.5F - topHalfWidth, y + height, z + 0.5F + topHalfWidth, maxU - topUAdjust, minV);
    		break;
    	case NORTH:
        	tessellator.addVertexWithUV(x + 0.5F + topHalfWidth, y + height, z + 0.5F - topHalfWidth, minU + topUAdjust, minV);
        	tessellator.addVertexWithUV(x + 1.0F, y + 0.0F, z + 0.0F, minU, maxV);
        	tessellator.addVertexWithUV(x + 0.0F, y + 0.0F, z + 0.0F, maxU, maxV);
        	tessellator.addVertexWithUV(x + 0.5F - topHalfWidth, y + height, z + 0.5F - topHalfWidth, maxU - topUAdjust, minV);
        	break;
    	case SOUTH:
        	tessellator.addVertexWithUV(x + 0.5F - topHalfWidth, y + height, z + 0.5F + topHalfWidth, minU + topUAdjust, minV);
        	tessellator.addVertexWithUV(x + 0.0F, y + 0.0F, z + 1.0F, minU, maxV);
        	tessellator.addVertexWithUV(x + 1.0F, y + 0.0F, z + 1.0F, maxU, maxV);
        	tessellator.addVertexWithUV(x + 0.5F + topHalfWidth, y + height, z + 0.5F + topHalfWidth, maxU - topUAdjust, minV);
        	break;
    	case EAST:
        	tessellator.addVertexWithUV(x + 0.5F + topHalfWidth, y + height, z + 0.5F + topHalfWidth, minU + topUAdjust, minV);
        	tessellator.addVertexWithUV(x + 1.0F, y + 0.0F, z + 1.0F, minU, maxV);
        	tessellator.addVertexWithUV(x + 1.0F, y + 0.0F, z + 0.0F, maxU, maxV);
        	tessellator.addVertexWithUV(x + 0.5F + topHalfWidth, y + height, z + 0.5F - topHalfWidth, maxU - topUAdjust, minV);
        	break;
    	case TOP:
        	tessellator.addVertexWithUV(x + 0.5F - topHalfWidth, y + height, z + 0.5F - topHalfWidth, minU + topUAdjust, minV + topUAdjust);
        	tessellator.addVertexWithUV(x + 0.5F - topHalfWidth, y + height, z + 0.5F + topHalfWidth, minU + topUAdjust, maxV - topUAdjust);
        	tessellator.addVertexWithUV(x + 0.5F + topHalfWidth, y + height, z + 0.5F + topHalfWidth, maxU - topUAdjust, maxV - topUAdjust);
        	tessellator.addVertexWithUV(x + 0.5F + topHalfWidth, y + height, z + 0.5F - topHalfWidth, maxU - topUAdjust, minV + topUAdjust);
        	break;
    	case BOTTOM:
        	tessellator.addVertexWithUV(x + 0.0F, y + 0.0F, z + 0.0F, minU, minV);
        	tessellator.addVertexWithUV(x + 1.0F, y + 0.0F, z + 0.0F, maxU, minV);
        	tessellator.addVertexWithUV(x + 1.0F, y + 0.0F, z + 1.0F, maxU, maxV);
        	tessellator.addVertexWithUV(x + 0.0F, y + 0.0F, z + 1.0F, minU, maxV);
    		break;
   		
    	}
		
	}

	public boolean shouldRender3DInInventory()
    {
         return true;
    }

    public int getRenderId()
    {
         return 0;
    }
}