package itemtransformhelper.fabric.mixin;

import itemtransformhelper.MenuItemCameraTransforms;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static itemtransformhelper.StartupClientOnly.clientTickHandler;
import static itemtransformhelper.fabric.MenuItemCameraTransformsImpl.HANDLERS;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V"))
    public void tick(CallbackInfo ci) {
        clientTickHandler.clientTickEvent();
        HANDLERS.forEach(MenuItemCameraTransforms.MenuKeyHandler::clientTick);
    }
}
