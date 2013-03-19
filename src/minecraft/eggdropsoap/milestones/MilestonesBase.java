package eggdropsoap.milestones;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="Milestones", name="Milestones & Trail Markers", version="0.1.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class MilestonesBase {

	public final static Block cobbleStoneMilestone = new MilestoneBlock(500, Block.cobblestone, 0).setBlockName("cobbleStoneMilestone").setHardness(1.0F);
	public final static Block mossStoneMilestone = new MilestoneBlock(501, Block.cobblestoneMossy, 0).setBlockName("mossStoneMilestone").setHardness(1.0F);
	public final static Block sandStoneMilestone = new MilestoneBlock(502, Block.sandStone, 0).setBlockName("sandStoneMilestone").setHardness(1.0F);
	public final static Block chiseledSandStoneMilestone = new MilestoneBlock(503, Block.sandStone, 0).setBlockName("chiseledSandStoneMilestone").setHardness(1.0F);
	public final static Block smoothSandStoneMilestone = new MilestoneBlock(504, Block.sandStone, 0).setBlockName("smoothSandStoneMilestone").setHardness(1.0F);
	public final static Block stoneBrickMilestone = new MilestoneBlock(505, Block.stoneBrick, 0).setBlockName("stoneBrickMilestone").setHardness(1.0F);
	public final static Block brickMilestone = new MilestoneBlock(506, Block.brick, 0).setBlockName("brickMilestone").setHardness(1.0F);
	public final static Block netherBrickMilestone = new MilestoneBlock(507, Block.netherBrick, 0).setBlockName("netherBrickMilestone").setHardness(1.0F);
	public final static Block obsidianMilestone = new MilestoneBlock(508, Block.obsidian, 0).setBlockName("obsidianMilestone").setHardness(1.0F);
	
	public static int truncatedPyramidRenderId;
	
    // The instance of your mod that Forge uses.
    @Instance("Milestones")
    public static MilestonesBase instance;
   
    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide="eggdropsoap.milestones.client.ClientProxy", serverSide="eggdropsoap.milestones.CommonProxy")
    public static CommonProxy proxy;
   
    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
            // Stub Method
    }
   
    @Init
    public void load(FMLInitializationEvent event) {

        this.registerBlock("Cobble Milestone", cobbleStoneMilestone, "cobbleStoneMilestone", Block.cobblestone);
        this.registerBlock("Mossy Cobble Milestone", mossStoneMilestone, "mossStoneMilestone", Block.cobblestoneMossy);
        this.registerBlock("Sandstone Milestone", sandStoneMilestone, "sandStoneMilestone", Block.sandStone);
        this.registerBlock("Chiseled Sandstone Milestone", chiseledSandStoneMilestone, "chiseledSandStoneMilestone", Block.sandStone, 1);
        this.registerBlock("Smooth Sandstone Milestone", smoothSandStoneMilestone, "smoothSandStoneMilestone", Block.sandStone, 2);
        this.registerBlock("Stone Brick Milestone", stoneBrickMilestone, "stoneBrickMilestone", Block.stoneBrick);
        this.registerBlock("Brick Milestone", brickMilestone, "brickMilestone", Block.brick);
        this.registerBlock("Nether Brick Milestone", netherBrickMilestone, "netherBrickMilestone", Block.netherBrick);      
        this.registerBlock("Obsidian Milestone", obsidianMilestone, "obsidianMilestone", Block.obsidian);      
   	
    	proxy.registerRenderers();
    }

    private void registerBlock(String name, Block newBlock, String registerName, Block recipeBlock) {
    	this.registerBlockWithMetadata(name, newBlock, registerName, recipeBlock, 0);
    }

    private void registerBlock(String name, Block newBlock, String registerName, Block recipeBlock, int recipeMetadata) {
    	this.registerBlockWithMetadata(name, newBlock, registerName, recipeBlock, recipeMetadata);
    }
  
    private void registerBlockWithMetadata(String name, Block newBlock, String registerName, Block recipeBlock, int recipeMetadata) {
    	LanguageRegistry.addName(newBlock, name);
    	
    	// make harvesting need a tool 1 less than for original block, minimum of 1 (stone)
    	int harvestLevel = MinecraftForge.getBlockHarvestLevel(recipeBlock, recipeMetadata, "pickaxe");
    	harvestLevel--;
    	harvestLevel = harvestLevel < 1 ? 1 : harvestLevel;
        MinecraftForge.setBlockHarvestLevel(newBlock, "pickaxe", harvestLevel);

        GameRegistry.registerBlock(newBlock, registerName);
        // recipe
        ItemStack recipeStack = new ItemStack(recipeBlock, 1, recipeMetadata);
        GameRegistry.addRecipe(new ItemStack(newBlock, 4), " x ", "xxx", 'x', recipeStack);
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
            // Stub Method
    }
}
