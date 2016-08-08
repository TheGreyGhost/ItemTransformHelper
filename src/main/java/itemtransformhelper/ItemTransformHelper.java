package itemtransformhelper;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ItemTransformHelper.MODID, version = ItemTransformHelper.VERSION)
public class ItemTransformHelper
{
    public static final String MODID = "itemtransformhelper";
    public static final String VERSION = "1.10.2a";

    // The instance of your mod that Forge uses.  Optional.
    @Mod.Instance(ItemTransformHelper.MODID)
    public static ItemTransformHelper instance;

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide="itemtransformhelper.ClientOnlyProxy", serverSide="itemtransformhelper.DedicatedServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
      proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
      proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
      proxy.postInit();
    }
}
