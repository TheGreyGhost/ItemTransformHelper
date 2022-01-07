package itemtransformhelper;

import dev.architectury.injectables.annotations.ExpectPlatform;

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

    public static final ModelBakeEventHandler modelBakeEventHandler = new ModelBakeEventHandler();

    public static final ClientTickHandler clientTickHandler = new ClientTickHandler();

    public static final MenuItemCameraTransforms menuItemCameraTransforms = new MenuItemCameraTransforms();

    @ExpectPlatform
    public static void clientSetup() {
        throw new UnsupportedOperationException();
    }

}
