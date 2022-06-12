package itemtransformhelper;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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

    // Registries
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MODID, Registry.ITEM_REGISTRY);

    // Items
    public static final RegistrySupplier<ItemCamera> ITEM_CAMERA = ITEMS.register("item_camera", ItemCamera::new);

    // Item Groups
    public static final CreativeModeTab ITH_ITEM_GROUP =
            CreativeTabRegistry.create(new ResourceLocation(MODID, "items"), () -> new ItemStack(ITEM_CAMERA.get()));

    public static void init() {
        ITEMS.register();
    }

}
