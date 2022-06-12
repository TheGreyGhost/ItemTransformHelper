package itemtransformhelper.quilt.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import itemtransformhelper.fabric.MenuItemCameraTransformsImpl;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderEffects"
            + "(Lcom/mojang/blaze3d/vertex/PoseStack;)V"))
    public void render(PoseStack matrices, float tickDelta, CallbackInfo ci) {
        MenuItemCameraTransformsImpl.RENDERERS.forEach(r -> r.displayHUDText(matrices));
    }

}
