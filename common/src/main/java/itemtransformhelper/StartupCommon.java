package itemtransformhelper;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MODID, Registry.ITEM_KEY);

    public static final RegistrySupplier<ItemCamera> ITEM_CAMERA = ITEMS.register("item_camera", ItemCamera::new);

    public static final ItemGroup ITH_ITEM_GROUP =
            CreativeTabRegistry.create(new Identifier(MODID, "items"), () -> new ItemStack(ITEM_CAMERA.get()));

    public static void init() {
        ITEMS.register();
    }

}
