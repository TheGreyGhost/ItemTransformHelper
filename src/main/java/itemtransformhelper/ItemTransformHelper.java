package itemtransformhelper;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ItemTransformHelper.MODID)
public class ItemTransformHelper {
    public static final String MODID = "itemtransformhelper";
    public static final Logger logger = LogManager.getLogger(MODID);

    public ItemTransformHelper() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        modEventBus.addListener(this::setup);

        // Register the doClientStuff method for modloading
        modEventBus.addListener(this::clientSetup);

        StartupCommon.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(final FMLCommonSetupEvent event) {
        // nothing left to do here with item reg moved to deferred reg
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        StartupClientOnly.clientSetup();
    }
}
