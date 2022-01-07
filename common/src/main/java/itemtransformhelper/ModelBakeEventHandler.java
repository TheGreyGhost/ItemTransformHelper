package itemtransformhelper;

import java.util.Map;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.util.Identifier;

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
        itemOverrideLink.forcedTransform = new ModelTransformation(
                Transformation.IDENTITY, Transformation.IDENTITY,
                Transformation.IDENTITY, Transformation.IDENTITY,
                Transformation.IDENTITY, Transformation.IDENTITY,
                Transformation.IDENTITY, Transformation.IDENTITY);
    }

    public void modelBakeEvent(Map<Identifier, BakedModel> modelRegistry) {
        for (Identifier modelKey : modelRegistry.keySet()) {
            BakedModel bakedModel = modelRegistry.get(modelKey);
            ItemModelFlexibleCamera wrappedModel = new ItemModelFlexibleCamera(bakedModel, itemOverrideLink);
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
