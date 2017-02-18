package itemtransformhelper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * User: The Grey Ghost
 * Date: 26/01/2015
 */
public class CreativeTabITH extends CreativeTabs
{
  public CreativeTabITH(int id, String unlocalizedName) {
    super(id, unlocalizedName);
  }

  @SideOnly(Side.CLIENT)
  public ItemStack getTabIconItem() {
    return new ItemStack(StartupCommon.itemCamera);
  }
}
