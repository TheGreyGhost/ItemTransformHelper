package itemtransformhelper;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * User: The Grey Ghost
 * Date: 26/01/2015
 * ItemCamera is very simple item used to activate the ItemCameraTransforms override when it is held in the hotbar.
 * See the Notes.
 */
public class ItemCamera extends Item
{
  public ItemCamera(Properties properties)
  {
    super(properties);
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
  {
    tooltip.add(new StringTextComponent("1) Place the camera in your hotbar"));
    tooltip.add(new StringTextComponent("2) Hold an item in your hand"));
    tooltip.add(new StringTextComponent("3) Use the cursor keys to "));
    tooltip.add(new StringTextComponent("   modify the item transform."));
  }
}
