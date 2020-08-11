package itemtransformhelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * User: The Grey Ghost
 * Date: 2/11/13
 * Every tick, check all the items in the hotbar to see if any are the camera  If so, apply the transform
 *   override to the held item.
 */
@SuppressWarnings("deprecation")
public class ClientTickHandler
{
  @SubscribeEvent
  public void clientTickEvent(TickEvent.ClientTickEvent event) {
    if (event.phase != TickEvent.Phase.START) {
      return;
    }

    ClientPlayerEntity player = Minecraft.getInstance().player;
    if (player == null) return;

    boolean foundCamera = false;
    PlayerInventory inventoryPlayer = player.inventory;
    for (int i = 0; i < PlayerInventory.getHotbarSize(); ++i) {
      ItemStack slotItemStack = inventoryPlayer.mainInventory.get(i);
      if (slotItemStack.getItem() == StartupCommon.ITEM_CAMERA.get()) {
        foundCamera = true;
        break;
      }
    }
    StartupClientOnly.menuItemCameraTransforms.changeMenuVisible(foundCamera);

    IBakedModel ibakedmodel = null;
    if (foundCamera) {
      ItemStack heldItemStack = player.getHeldItem(Hand.MAIN_HAND);
      if (heldItemStack.isEmpty()) {
        heldItemStack = player.getHeldItem(Hand.OFF_HAND);
      }
      if (!heldItemStack.isEmpty()) {
        ibakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(heldItemStack);
      }
    }

    ItemModelFlexibleCamera.UpdateLink link = StartupClientOnly.modelBakeEventHandler.getItemOverrideLink();
    link.itemModelToOverride = ibakedmodel;
    link.forcedTransform = StartupClientOnly.menuItemCameraTransforms.getItemCameraTransforms();
  }
}
