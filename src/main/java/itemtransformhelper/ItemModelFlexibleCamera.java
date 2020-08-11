package itemtransformhelper;


import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemTransformVec3f;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.model.TransformationHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * User: The Grey Ghost
 * Date: 20/01/2015
 * This class is a simple wrapper to substitute a new set of camera transforms for an existing item
 * Usage:
 * 1) Construct a new ItemModelFlexibleCamera with the model to wrap and an UpdateLink using getWrappedModel()
 * 2) Replace the ItemModelFlexibleCamera into the modelRegistry in place of the model to wrap
 * 3) Alter the UpdateLink to control the ItemCameraTransform of a given model:
 *   a) itemModelToOverride selects the item to be overridden
 *   b) forcedTransform is the transform to apply
 * Models which don't match itemModelToOverride will use their original transform
 *
 * NB Starting with Forge 1.8-11.14.4.1563, it appears that all items now implement IPerspectiveAwareModel
 */
@SuppressWarnings({ "deprecation", "unchecked" })
public class ItemModelFlexibleCamera extends BakedModelWrapper
{
	protected final UpdateLink updateLink;

	public ItemModelFlexibleCamera(IBakedModel i_modelToWrap, UpdateLink linkToCurrentInformation)
	{
		super(i_modelToWrap);
		updateLink = linkToCurrentInformation;
	}

	@Nonnull
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
		return super.getQuads(state, side, rand, EmptyModelData.INSTANCE);
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return (updateLink.itemModelToOverride == this) ? updateLink.forcedTransform : originalModel.getItemCameraTransforms();
	}

	@Override
	public boolean doesHandlePerspectives() {
		if (updateLink.itemModelToOverride == this) {
			return true;
		}
		return originalModel.doesHandlePerspectives();
	}

	// none of this should be needed anymore

	@Override
	public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
		if (updateLink.itemModelToOverride == this) {
			return handlePerspectiveOther(cameraTransformType, mat);
		} else {
			return super.handlePerspective(cameraTransformType, mat);
		}
	}

	public IBakedModel handlePerspectiveOther(ItemCameraTransforms.TransformType type, MatrixStack stack)
	{
		TransformationMatrix tr = TransformationHelper.toTransformation(getItemCameraTransforms().getTransform(type));
		if(!tr.isIdentity()) {
			tr.push(stack);
		}
		return this;
	}

	public IBakedModel getIBakedModel() {
		return originalModel;
	}

	public static class UpdateLink
	{
		public IBakedModel itemModelToOverride;
		public ItemCameraTransforms forcedTransform;
	}
}
