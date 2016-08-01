package itemtransformhelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.common.MinecraftForge;

/**
 * User: The Grey Ghost
 * Date: 24/12/2014
 *
 * The Startup classes for this example are called during startup, in the following order:
 *  preInitCommon
 *  preInitClientOnly
 *  initCommon
 *  initClientOnly
 *  postInitCommon
 *  postInitClientOnly
 *  See MinecraftByExample class for more information
 */
public class StartupClientOnly
{
  public final static ModelBakeEventHandler modelBakeEventHandler = new ModelBakeEventHandler();
  public final static ClientTickHandler clientTickHandler = new ClientTickHandler();
  public static MenuItemCameraTransforms menuItemCameraTransforms;

  public static void preInitClientOnly()
  {
    MinecraftForge.EVENT_BUS.register(modelBakeEventHandler);
    MinecraftForge.EVENT_BUS.register(clientTickHandler);
  }

  public static void initClientOnly()
  {
    // required in order for the renderer to know how to render your item.  Likely to change in the near future.
    ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("itemtransformhelper:item_camera", "inventory");
    final int DEFAULT_ITEM_SUBTYPE = 0;
    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(StartupCommon.itemCamera, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
  }

  public static void postInitClientOnly()
  {
    menuItemCameraTransforms = new MenuItemCameraTransforms();
  }
}
