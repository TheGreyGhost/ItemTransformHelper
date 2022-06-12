package itemtransformhelper;

import java.util.Map;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

/**
 * User: The Grey Ghost
 * Date: 20/01/2015
 * We use the ModelBakeEvent to iterate through all the registered models, wrap each one in an
 * ItemModelFlexibleCamera, and write it
 * back into the registry.
 * Each wrapped model gets a reference to ModelBakeEventHandler::itemOverrideLink.
 * Later, we can alter the members of itemOverrideLink to change the ItemCameraTransforms for a desired Item
 */
public class ModelBakeEventHandler {

    private final ItemModelFlexibleCamera.UpdateLink itemOverrideLink = new ItemModelFlexibleCamera.UpdateLink();

    public ModelBakeEventHandler() {
        itemOverrideLink.forcedTransform = new ItemTransforms(
                ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM,
                ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM,
                ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM,
                ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM);
    }

    public void modelBakeEvent(Map<ResourceLocation, BakedModel> modelRegistry) {
        for (ResourceLocation modelKey : modelRegistry.keySet()) {
            BakedModel bakedModel = modelRegistry.get(modelKey);
            ItemModelFlexibleCamera wrappedModel = ItemModelFlexibleCamera.create(bakedModel, itemOverrideLink);
            modelRegistry.put(modelKey, wrappedModel);
        }

        ItemTransformHelper.logger.warn("Warning - The Item Transform Helper replaces your BakedModels with a "
                + "wrapped version, this");
        ItemTransformHelper.logger.warn("  is done even when the helper is not in your hotbar, and might cause "
                + "problems if your");
        ItemTransformHelper.logger.warn("  BakedModel implements an interface ItemTransformHelper doesn't know about"
                + ".");
        ItemTransformHelper.logger.warn("  I recommend you disable the mod when you're not actively using it to "
                + "transform your items.");
    }

    public ItemModelFlexibleCamera.UpdateLink getItemOverrideLink() {
        return itemOverrideLink;
    }

}
