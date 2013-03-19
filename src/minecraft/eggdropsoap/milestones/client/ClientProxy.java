package eggdropsoap.milestones.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import eggdropsoap.milestones.CommonProxy;
import eggdropsoap.milestones.MilestoneBlockRenderer;
import eggdropsoap.milestones.MilestonesBase;

public class ClientProxy extends CommonProxy {
       
        @Override
        public void registerRenderers() {
                MinecraftForgeClient.preloadTexture(ITEMS_PNG);
                MinecraftForgeClient.preloadTexture(BLOCK_PNG);

                MilestonesBase.truncatedPyramidRenderId = RenderingRegistry.instance().getNextAvailableRenderId();
//                MilestonesBase.block2ModelID = RenderingRegistry.instance().getNextAvailableRenderId();
//                MilestonesBase.block3ModelID = RenderingRegistry.instance().getNextAvailableRenderId();
                RenderingRegistry.instance().registerBlockHandler(MilestonesBase.truncatedPyramidRenderId , new MilestoneBlockRenderer());
//                RenderingRegistry.instance().registerBlockHandler(MilestonesBase.block2ModelID , new YourModRender());
//                RenderingRegistry.instance().registerBlockHandler(MilestonesBase.block3ModelID , new YourModRender());
        }
        
}