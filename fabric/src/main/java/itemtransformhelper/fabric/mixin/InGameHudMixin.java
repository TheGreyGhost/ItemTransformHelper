package itemtransformhelper.fabric.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static itemtransformhelper.fabric.MenuItemCameraTransformsImpl.RENDERERS;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;"
            + "renderStatusEffectOverlay(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        RENDERERS.forEach(r -> r.displayHUDText(matrices));
    }

}
