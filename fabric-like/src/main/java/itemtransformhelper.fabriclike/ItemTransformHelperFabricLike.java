package itemtransformhelper.fabriclike;

import itemtransformhelper.ItemTransformHelper;
import itemtransformhelper.StartupClientOnly;

public class ItemTransformHelperFabricLike {

    public static void init() {
        ItemTransformHelper.init();
        StartupClientOnly.clientSetup();
    }

}
