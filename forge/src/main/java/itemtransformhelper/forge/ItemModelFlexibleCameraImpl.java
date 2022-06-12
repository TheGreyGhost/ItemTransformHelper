package itemtransformhelper.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import itemtransformhelper.ItemModelFlexibleCamera;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
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
    public BakedModel handlePerspective(@NotNull ItemTransforms.TransformType cameraTransformType,
                                        @NotNull PoseStack poseStack) {
        if (updateLink.itemModelToOverride == this) {
            Transformation tr =
                    TransformationHelper.toTransformation(getTransforms().getTransform(cameraTransformType));
            if (!tr.isIdentity()) {
                tr.push(poseStack);
            }
            return this;
        } else {
            return super.handlePerspective(cameraTransformType, poseStack);
        }
    }

}
