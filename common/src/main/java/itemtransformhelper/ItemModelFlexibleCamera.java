package itemtransformhelper;


import dev.architectury.injectables.annotations.ExpectPlatform;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: The Grey Ghost
 * Date: 20/01/2015
 * This class is a simple wrapper to substitute a new set of camera transforms for an existing item
 * Usage:
 * 1) Construct a new ItemModelFlexibleCamera with the model to wrap and an UpdateLink using getWrappedModel()
 * 2) Replace the ItemModelFlexibleCamera into the modelRegistry in place of the model to wrap
 * 3) Alter the UpdateLink to control the ItemCameraTransform of a given model:
 * a) itemModelToOverride selects the item to be overridden
 * b) forcedTransform is the transform to apply
 * Models which don't match itemModelToOverride will use their original transform
 * <p>
 * NB Starting with Forge 1.8-11.14.4.1563, it appears that all items now implement IPerspectiveAwareModel
 */
public abstract class ItemModelFlexibleCamera implements BakedModel {

    protected final BakedModel originalModel;
    protected final UpdateLink updateLink;

    protected ItemModelFlexibleCamera(BakedModel originalModel, UpdateLink updateLink) {
        this.originalModel = originalModel;
        this.updateLink = updateLink;
    }

    @ExpectPlatform
    public static ItemModelFlexibleCamera create(BakedModel originalModel, UpdateLink updateLink) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public ModelTransformation getTransformation() {
        return (updateLink.itemModelToOverride == this)
                ? updateLink.forcedTransform
                : originalModel.getTransformation();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        return originalModel.getQuads(state, side, rand);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return originalModel.useAmbientOcclusion();
    }

    @Override
    public boolean hasDepth() {
        return originalModel.hasDepth();
    }

    @Override
    public boolean isSideLit() {
        return originalModel.isSideLit();
    }

    @Override
    public boolean isBuiltin() {
        return originalModel.isBuiltin();
    }

    @Override
    public Sprite getParticleSprite() {
        return originalModel.getParticleSprite();
    }

    @Override
    public ModelOverrideList getOverrides() {
        return originalModel.getOverrides();
    }

    public static class UpdateLink {

        public BakedModel itemModelToOverride;

        public ModelTransformation forcedTransform;

    }

}
