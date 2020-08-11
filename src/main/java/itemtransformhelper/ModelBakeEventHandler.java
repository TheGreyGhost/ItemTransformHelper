package itemtransformhelper;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemTransformVec3f;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

/**
 * User: The Grey Ghost
 * Date: 20/01/2015
 * We use the ModelBakeEvent to iterate through all the registered models, wrap each one in an ItemModelFlexibleCamera, and write it
 *   back into the registry.
 * Each wrapped model gets a reference to ModelBakeEventHandler::itemOverrideLink.
 * Later, we can alter the members of itemOverrideLink to change the ItemCameraTransforms for a desired Item
 */
@SuppressWarnings("deprecation")
public class ModelBakeEventHandler
{
  public ModelBakeEventHandler()
  {
    itemOverrideLink.forcedTransform = new ItemCameraTransforms(ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT,
                                                                ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT,
                                                                ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT,
                                                                ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT);
  }

  @SubscribeEvent
  public void modelBakeEvent(ModelBakeEvent event)
  {
    Map<ResourceLocation, IBakedModel> modelRegistry = event.getModelRegistry();
    for (ResourceLocation modelKey : modelRegistry.keySet()) {
      IBakedModel iBakedModel = event.getModelRegistry().get(modelKey);
      ItemModelFlexibleCamera wrappedModel = new ItemModelFlexibleCamera(iBakedModel, itemOverrideLink);
      event.getModelRegistry().put(modelKey, wrappedModel);
    }
    ItemTransformHelper.logger.info("Warning - The Item Transform Helper replaces your IBakedModels with a wrapped version, this");
    ItemTransformHelper.logger.info("  is done even when the helper is not in your hotbar, and might cause problems if your");
    ItemTransformHelper.logger.info("  IBakedModel implements an interface ItemTransformHelper doesn't know about.");
    ItemTransformHelper.logger.info("  I recommend you disable the mod when you're not actively using it to transform your items.");
  }

  public ItemModelFlexibleCamera.UpdateLink getItemOverrideLink() {
    return itemOverrideLink;
  }

  private ItemModelFlexibleCamera.UpdateLink itemOverrideLink = new ItemModelFlexibleCamera.UpdateLink();
}
