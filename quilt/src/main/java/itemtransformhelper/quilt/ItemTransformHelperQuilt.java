package itemtransformhelper.quilt;

import itemtransformhelper.fabriclike.ItemTransformHelperFabricLike;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class ItemTransformHelperQuilt implements ModInitializer {

    @Override
    public void onInitialize(ModContainer mod) {
        ItemTransformHelperFabricLike.init();
    }

}
