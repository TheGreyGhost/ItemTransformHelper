package itemtransformhelper.fabric;

import itemtransformhelper.ItemModelFlexibleCamera;
import net.minecraft.client.resources.model.BakedModel;

public class ItemModelFlexibleCameraImpl extends ItemModelFlexibleCamera {

    public ItemModelFlexibleCameraImpl(BakedModel originalModel, UpdateLink updateLink) {
        super(originalModel, updateLink);
    }

    public static ItemModelFlexibleCamera create(BakedModel originalModel,
                                                 UpdateLink updateLink) {
        return new ItemModelFlexibleCameraImpl(originalModel, updateLink);
    }

}
