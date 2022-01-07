package itemtransformhelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

/**
 * User: The Grey Ghost
 * Date: 2/11/13
 * Every tick, check all the items in the hotbar to see if any are the camera  If so, apply the transform
 * override to the held item.
 */
public class ClientTickHandler {

    public void clientTickEvent() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        boolean foundCamera = false;
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < PlayerInventory.getHotbarSize(); ++i) {
            ItemStack stack = inventory.main.get(i);
            if (stack.getItem() == StartupCommon.ITEM_CAMERA.get()) {
                foundCamera = true;
                break;
            }
        }
        StartupClientOnly.menuItemCameraTransforms.changeMenuVisible(foundCamera);

        BakedModel bakedModel = null;
        if (foundCamera) {
            ItemStack heldItemStack = player.getEquippedStack(EquipmentSlot.MAINHAND);
            if (heldItemStack.isEmpty()) {
                heldItemStack = player.getEquippedStack(EquipmentSlot.OFFHAND);
            }
            if (!heldItemStack.isEmpty()) {
                bakedModel = MinecraftClient.getInstance().getItemRenderer().getModels().getModel(heldItemStack);
            }
        }

        ItemModelFlexibleCamera.UpdateLink link = StartupClientOnly.modelBakeEventHandler.getItemOverrideLink();
        link.itemModelToOverride = bakedModel;
        link.forcedTransform = StartupClientOnly.menuItemCameraTransforms.getItemCameraTransforms();
    }

}
