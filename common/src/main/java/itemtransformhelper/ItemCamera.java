package itemtransformhelper;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: The Grey Ghost
 * Date: 26/01/2015
 * ItemCamera is very simple item used to activate the ItemCameraTransforms override when it is held in the hotbar.
 * See the Notes.
 */
public class ItemCamera extends Item {

    public ItemCamera() {
        super(new Properties().stacksTo(1).tab(StartupCommon.ITH_ITEM_GROUP));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list,
                                @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.literal("1) Place the camera in your hotbar"));
        list.add(Component.literal("2) Hold an item in your hand"));
        list.add(Component.literal("3) Use the cursor keys to"));
        list.add(Component.literal("   modify the item transform."));
    }

}
