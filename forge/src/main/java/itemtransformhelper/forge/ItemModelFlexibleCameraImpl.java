package itemtransformhelper.forge;

import itemtransformhelper.ItemModelFlexibleCamera;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.AffineTransformation;
import net.minecraftforge.common.model.TransformationHelper;
import org.jetbrains.annotations.NotNull;

public class ItemModelFlexibleCameraImpl extends ItemModelFlexibleCamera {

    public ItemModelFlexibleCameraImpl(BakedModel originalModel, UpdateLink updateLink) {
        super(originalModel, updateLink);
    }

    public static ItemModelFlexibleCamera create(BakedModel originalModel,
                                                 ItemModelFlexibleCamera.UpdateLink updateLink) {
        return new ItemModelFlexibleCameraImpl(originalModel, updateLink);
    }

    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public BakedModel handlePerspective(@NotNull ModelTransformation.Mode cameraTransformType,
                                        @NotNull MatrixStack poseStack) {
        if (updateLink.itemModelToOverride == this) {
            AffineTransformation tr =
                    TransformationHelper.toTransformation(getTransformation().getTransformation(cameraTransformType));
            if (!tr.isIdentity()) {
                tr.push(poseStack);
            }
            return this;
        } else {
            return super.handlePerspective(cameraTransformType, poseStack);
        }
    }

}
