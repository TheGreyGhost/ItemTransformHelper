package itemtransformhelper.forge;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static itemtransformhelper.StartupClientOnly.clientTickHandler;
import static itemtransformhelper.StartupClientOnly.modelBakeEventHandler;

public class StartupClientOnlyImpl {

    public static void clientSetup() {
        StartupClientOnlyImpl instance = new StartupClientOnlyImpl();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(instance::modelBakeEvent);
        MinecraftForge.EVENT_BUS.register(instance);
    }

    @SubscribeEvent
    public void modelBakeEvent(ModelBakeEvent event) {
        modelBakeEventHandler.modelBakeEvent(event.getModelRegistry());
    }

    @SubscribeEvent
    public void clientTickEvent(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            clientTickHandler.clientTickEvent();
        }
    }

}
