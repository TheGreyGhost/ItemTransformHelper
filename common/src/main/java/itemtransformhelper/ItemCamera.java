package itemtransformhelper;

import java.util.List;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * User: The Grey Ghost
 * Date: 26/01/2015
 * ItemCamera is very simple item used to activate the ItemCameraTransforms override when it is held in the hotbar.
 * See the Notes.
 */
public class ItemCamera extends Item {

    public ItemCamera() {
        super(new Settings().maxCount(1).group(StartupCommon.ITH_ITEM_GROUP));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("1) Place the camera in your hotbar"));
        tooltip.add(Text.literal("2) Hold an item in your hand"));
        tooltip.add(Text.literal("3) Use the cursor keys to"));
        tooltip.add(Text.literal("   modify the item transform."));
    }

}
