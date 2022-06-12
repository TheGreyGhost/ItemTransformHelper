package itemtransformhelper.fabric.mixin;

import java.util.Map;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static itemtransformhelper.StartupClientOnly.modelBakeEventHandler;

@Mixin(ModelManager.class)
public class ModelManagerMixin {

    @Shadow
    private Map<ResourceLocation, BakedModel> bakedRegistry;

    @Inject(method = "apply(Lnet/minecraft/client/resources/model/ModelBakery;"
            + "Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush"
                    + "(Ljava/lang/String;)V"))
    public void apply(ModelBakery modelLoader, ResourceManager resourceManager, ProfilerFiller profiler,
                      CallbackInfo ci) {
        modelBakeEventHandler.modelBakeEvent(bakedRegistry);
    }

}
