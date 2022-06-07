package itemtransformhelper.fabric;

import itemtransformhelper.fabriclike.ItemTransformHelperFabricLike;
import net.fabricmc.api.ModInitializer;

public class ItemTransformHelperFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ItemTransformHelperFabricLike.init();
    }

}
