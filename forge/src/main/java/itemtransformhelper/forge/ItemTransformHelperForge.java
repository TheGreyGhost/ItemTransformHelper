package itemtransformhelper.forge;

import dev.architectury.platform.forge.EventBuses;
import itemtransformhelper.StartupClientOnly;
import itemtransformhelper.StartupCommon;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static itemtransformhelper.ItemTransformHelper.MODID;

@Mod(MODID)
public class ItemTransformHelperForge {

    public ItemTransformHelperForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(MODID, modEventBus);

        // Register the setup method for modloading
        modEventBus.addListener(this::setup);

        // Register the doClientStuff method for modloading
        modEventBus.addListener(this::clientSetup);

        StartupCommon.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        // nothing left to do here with item reg moved to deferred reg
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        StartupClientOnly.clientSetup();
    }

}
