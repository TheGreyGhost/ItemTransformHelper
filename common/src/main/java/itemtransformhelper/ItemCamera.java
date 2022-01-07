package itemtransformhelper;

import java.util.List;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
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
        tooltip.add(new LiteralText("1) Place the camera in your hotbar"));
        tooltip.add(new LiteralText("2) Hold an item in your hand"));
        tooltip.add(new LiteralText("3) Use the cursor keys to"));
        tooltip.add(new LiteralText("   modify the item transform."));
    }

}
