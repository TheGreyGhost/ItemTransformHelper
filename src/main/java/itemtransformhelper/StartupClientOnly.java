package itemtransformhelper;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * User: The Grey Ghost
 * Date: 24/12/2014
 * <p>
 * The Startup classes for this example are called during startup, in the following order:
 * preInitCommon
 * preInitClientOnly
 * initCommon
 * initClientOnly
 * postInitCommon
 * postInitClientOnly
 * See MinecraftByExample class for more information
 */
public class StartupClientOnly {
    public final static ModelBakeEventHandler modelBakeEventHandler = new ModelBakeEventHandler();
    public final static ClientTickHandler clientTickHandler = new ClientTickHandler();
    public static MenuItemCameraTransforms menuItemCameraTransforms;

    public static void clientSetup() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(modelBakeEventHandler::modelBakeEvent);
        MinecraftForge.EVENT_BUS.register(clientTickHandler);
        menuItemCameraTransforms = new MenuItemCameraTransforms();
    }
}