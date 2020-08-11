package itemtransformhelper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static itemtransformhelper.ItemTransformHelper.MODID;

/**
 * User: The Grey Ghost
 * Date: 24/12/2014
 * <p>
 * The Startup classes for this example are called during startup, in the following order:
 * preInitCommon
 * preInitClientOnly
 * initCommon
 * initClientOnly
 * postInitCommon
 * postInitClientOnly
 * See MinecraftByExample class for more information
 */
public class StartupCommon {
    public static ItemGroup tabITH = new CreativeTabITH("itemtransformhelper");

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<ItemCamera> ITEM_CAMERA = ITEMS.register("item_camera", () -> new ItemCamera(
            new Item.Properties().group(tabITH).maxStackSize(1)));
}