package itemtransformhelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

/**
 * User: The Grey Ghost
 * Date: 2/11/13
 * Every tick, check all the items in the hotbar to see if any are the camera  If so, apply the transform
 * override to the held item.
 */
public class ClientTickHandler {

    public void clientTickEvent() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        boolean foundCamera = false;
        Inventory inventory = player.getInventory();
        for (int i = 0; i < Inventory.getSelectionSize(); ++i) {
            ItemStack stack = inventory.items.get(i);
            if (stack.getItem() == StartupCommon.ITEM_CAMERA.get()) {
                foundCamera = true;
                break;
            }
        }
        StartupClientOnly.menuItemCameraTransforms.changeMenuVisible(foundCamera);

        BakedModel bakedModel = null;
        if (foundCamera) {
            ItemStack heldItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (heldItemStack.isEmpty()) {
                heldItemStack = player.getItemInHand(InteractionHand.OFF_HAND);
            }
            if (!heldItemStack.isEmpty()) {
                bakedModel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(heldItemStack);
            }
        }

        ItemModelFlexibleCamera.UpdateLink link = StartupClientOnly.modelBakeEventHandler.getItemOverrideLink();
        link.itemModelToOverride = bakedModel;
        link.forcedTransform = StartupClientOnly.menuItemCameraTransforms.getItemCameraTransforms();
    }

}
