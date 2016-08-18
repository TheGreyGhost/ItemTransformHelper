package itemtransformhelper;


import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;

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
 * The wrapper currently understands IPerspectiveAwareModel
 * It can't be used for Forge Universal Buckets (BakedDynBucket) because of an unsafe cast in the forge code and because
 *    BakedDynBucket is a final class and private as well.
 *
 * NB Starting with Forge 1.8-11.14.4.1563, it appears that all items now implement IPerspectiveAwareModel
 */
@SuppressWarnings({ "deprecation", "unchecked" })
public class ItemModelFlexibleCamera implements IBakedModel
{
  /**
   * Can this model be wrapped in an ItemModelFlexibleCamera?  (eg BakedDynBucket currently can't).
   * @param modelToWrap
   * @return true if this model can be safely wrapped
   */
  public static boolean canBeWrapped(IBakedModel modelToWrap)
  {
    if (bakedDynBucketClazz == null) { // lazy initialisation
      try {
        bakedDynBucketClazz = Class.forName("net.minecraftforge.client.model.ModelDynBucket$BakedDynBucket");
      } catch (ClassNotFoundException cnfe) {
        System.err.println("Couldn't initialise bakedDynBucketClazz:" + cnfe);
        bakedDynBucketClazz = ItemTransformHelper.class; // arbitrary class to stop error message more than once
      }
    }
    if (bakedDynBucketClazz.isInstance(modelToWrap)) {
     return false; // breakpoint here
    }
    return true;
  }

  public static ItemModelFlexibleCamera getWrappedModel(IBakedModel modelToWrap, UpdateLink linkToCurrentInformation)
  {
    if (modelToWrap instanceof IPerspectiveAwareModel) {
      return new ItemModelFlexibleCameraPerspectiveAware(modelToWrap, linkToCurrentInformation);
    } else {
      return new ItemModelFlexibleCamera(modelToWrap, linkToCurrentInformation);
    }
  }

  private static Class<?> bakedDynBucketClazz = null;  // lazy initialisation in canBeWrapped()

  private ItemModelFlexibleCamera(IBakedModel i_modelToWrap, UpdateLink linkToCurrentInformation)
  {
    updateLink = linkToCurrentInformation;
    iBakedModel = i_modelToWrap;
  }


//
//  @Override
//  public List getFaceQuads(EnumFacing enumFacing) {
//    return iBakedModel.getFaceQuads(enumFacing);
//  }
//
//  @Override
//  public List getGeneralQuads() {
//    return iBakedModel.getGeneralQuads();
//  }

  @Override
  public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
  {
    return iBakedModel.getQuads(state, side, rand);
  }

  public ItemOverrideList getOverrides()
  {
    return iBakedModel.getOverrides();
  }



//  @Override
//  public VertexFormat getFormat() {
//    if (iBakedModel instanceof IFlexibleBakedModel) {
//      return ((IFlexibleBakedModel) iBakedModel).getFormat();
//    } else {
//      return Attributes.DEFAULT_BAKED_FORMAT;
//    }
//  }

  @Override
  public boolean isAmbientOcclusion() {
    return iBakedModel.isAmbientOcclusion();
  }

  @Override
  public boolean isGui3d() {
    return iBakedModel.isGui3d();
  }

  @Override
  public boolean isBuiltInRenderer() {
    return iBakedModel.isBuiltInRenderer();
  }

  @Override
  public TextureAtlasSprite getParticleTexture() {
    return iBakedModel.getParticleTexture();
  }

  @Override
  public ItemCameraTransforms getItemCameraTransforms() {
    return (updateLink.itemModelToOverride == this) ? updateLink.forcedTransform : iBakedModel.getItemCameraTransforms();
  }

  protected final UpdateLink updateLink;

  public IBakedModel getIBakedModel() {
    return iBakedModel;
  }

  private final IBakedModel iBakedModel;

  public static class ItemModelFlexibleCameraPerspectiveAware extends ItemModelFlexibleCamera implements IPerspectiveAwareModel
  {
    ItemModelFlexibleCameraPerspectiveAware(IBakedModel i_modelToWrap, UpdateLink linkToCurrentInformation)
    {
      super(i_modelToWrap, linkToCurrentInformation);
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
      if (updateLink.itemModelToOverride == this) {
        ItemTransformVec3f itemTransformVec3f;
        switch (cameraTransformType) {
          case NONE: {
              itemTransformVec3f = ItemTransformVec3f.DEFAULT;
              break;
          }
          case FIRST_PERSON_LEFT_HAND: {
                itemTransformVec3f = updateLink.forcedTransform.firstperson_left;
                break;
            }
          case FIRST_PERSON_RIGHT_HAND: {
            itemTransformVec3f = updateLink.forcedTransform.firstperson_right;
            break;
          }
          case THIRD_PERSON_LEFT_HAND: {
              itemTransformVec3f = updateLink.forcedTransform.thirdperson_left;
              break;
          }
          case THIRD_PERSON_RIGHT_HAND: {
            itemTransformVec3f = updateLink.forcedTransform.thirdperson_right;
            break;
          }
          case GUI: {
              itemTransformVec3f = updateLink.forcedTransform.gui;
              break;
          }
          case HEAD: {
              itemTransformVec3f = updateLink.forcedTransform.head;
              break;
          }
          case GROUND: {
            itemTransformVec3f = updateLink.forcedTransform.ground;
            break;
          }
          case FIXED: {
            itemTransformVec3f = updateLink.forcedTransform.fixed;
            break;
          }

          default: {
           throw new IllegalArgumentException("Unknown cameraTransformType:" + cameraTransformType);
          }
        }

        TRSRTransformation tr = new TRSRTransformation(itemTransformVec3f);
        Matrix4f mat = null;
        if (tr != null) { // && tr != TRSRTransformation.identity()) {
          mat = tr.getMatrix();
        }
        // The TRSRTransformation for vanilla items have blockCenterToCorner() applied, however handlePerspective
        //  reverses it back again with blockCornerToCenter().  So we don't need to apply it here.

        return Pair.of(this, mat);

      } else {
        IBakedModel baseModel = getIBakedModel();
        return ((IPerspectiveAwareModel) baseModel).handlePerspective(cameraTransformType);
      }
    }
  }

  public static class UpdateLink
  {
    public IBakedModel itemModelToOverride;
    public ItemCameraTransforms forcedTransform;
  }

}
