package itemtransformhelper.fabric;

import itemtransformhelper.StartupClientOnly;
import itemtransformhelper.StartupCommon;
import net.fabricmc.api.ModInitializer;

public class ItemTransformHelperFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        StartupCommon.init();
        StartupClientOnly.clientSetup();
    }

}
