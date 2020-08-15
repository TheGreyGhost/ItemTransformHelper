package itemtransformhelper;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * User: The Grey Ghost
 * Date: 26/01/2015
 */
public class CreativeTabITH extends ItemGroup {
    public CreativeTabITH(String unlocalizedName) {
        super(unlocalizedName);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemStack createIcon() {
        return new ItemStack(StartupCommon.ITEM_CAMERA.get());
    }
}