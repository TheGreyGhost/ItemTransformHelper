package itemtransformhelper.fabric;

import itemtransformhelper.ItemTransformHelper;
import itemtransformhelper.StartupClientOnly;
import net.fabricmc.api.ModInitializer;

public class ItemTransformHelperFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ItemTransformHelper.init();
        StartupClientOnly.clientSetup();
    }

}
