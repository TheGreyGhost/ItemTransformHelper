package itemtransformhelper;

import dev.architectury.injectables.annotations.ExpectPlatform;
import java.util.Locale;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;


/**
 * The menu used to select and alter the different parts of the ItemCameraTransform for the currently selected item.
 * The menu state is rendered on the screen by HUDtextRenderer.
 * The class registers its components on the Forge and FML event buses.
 * Created by TheGreyGhost on 22/01/15.
 */
public class MenuItemCameraTransforms {

    private static final Logger LOGGER = LogManager.getLogger();

    private final HUDTextRenderer.HUDInfoUpdateLink linkToHUDrenderer;

    private final MenuKeyHandler menuKeyHandler;

    public MenuItemCameraTransforms() {
        linkToHUDrenderer = new HUDTextRenderer.HUDInfoUpdateLink();
        menuKeyHandler = new MenuKeyHandler(this.new KeyPressCallback());
        registerListeners(new HUDTextRenderer(linkToHUDrenderer), menuKeyHandler);
    }

    @ExpectPlatform
    public static void registerListeners(HUDTextRenderer hudTextRenderer, MenuKeyHandler menuKeyHandler) {
        throw new UnsupportedOperationException();
    }

    /**
     * get the current ItemCameraTransforms
     *
     * @return the transform
     */
    public ModelTransformation getItemCameraTransforms() {
        return linkToHUDrenderer.itemCameraTransforms;
    }

    /**
     * turn menu on or off
     *
     * @param visible true = make visible
     */
    public void changeMenuVisible(boolean visible) {
        linkToHUDrenderer.menuVisible = visible;
    }

    public class KeyPressCallback {
        void keyPressed(MenuKeyHandler.ArrowKeys whichKey) {
            if (!linkToHUDrenderer.menuVisible) return;

            switch (whichKey) {
            case DOWN:
                linkToHUDrenderer.selectedField = linkToHUDrenderer.selectedField.getNextField();
                break;
            case UP:
                linkToHUDrenderer.selectedField = linkToHUDrenderer.selectedField.getPreviousField();
                break;
            case RIGHT:
            case LEFT:
                alterField(whichKey == MenuKeyHandler.ArrowKeys.RIGHT);
                break;
            case NONE:
            }
        }
    }

    private void alterField(boolean increase) {
        Transformation transformVec3f = getItemTransformRef(linkToHUDrenderer, linkToHUDrenderer.selectedTransform);
        if (transformVec3f == null) return; // should never happen

        final float SCALE_INCREMENT = 0.01F;
        final float ROTATION_INCREMENT = 2F;
        final float TRANSLATION_INCREMENT = 0.25F * 0.0625F; // 1/4 of a block, with multiplier from
        // ItemTransformVec3f::deserialize0()
        switch (linkToHUDrenderer.selectedField) {
        case TRANSFORM -> linkToHUDrenderer.selectedTransform = increase ? linkToHUDrenderer.selectedTransform.getNext()
                : linkToHUDrenderer.selectedTransform.getPrevious();
        case SCALE_X -> transformVec3f.scale.add(increase ? SCALE_INCREMENT : -SCALE_INCREMENT, 0, 0);
        case SCALE_Y -> transformVec3f.scale.add(0, increase ? SCALE_INCREMENT : -SCALE_INCREMENT, 0);
        case SCALE_Z -> transformVec3f.scale.add(0, 0, increase ? SCALE_INCREMENT : -SCALE_INCREMENT);
        case ROTATE_X -> {
            float newAngle = transformVec3f.rotation.getX() + (increase ? ROTATION_INCREMENT : -ROTATION_INCREMENT);
            newAngle = MathHelper.wrapDegrees(newAngle - 180) + 180;
            transformVec3f.rotation.set(newAngle, transformVec3f.rotation.getY(), transformVec3f.rotation.getZ());
        }
        case ROTATE_Y -> {
            float newAngle = transformVec3f.rotation.getY() + (increase ? ROTATION_INCREMENT : -ROTATION_INCREMENT);
            newAngle = MathHelper.wrapDegrees(newAngle - 180) + 180;
            transformVec3f.rotation.set(transformVec3f.rotation.getX(), newAngle, transformVec3f.rotation.getZ());
        }
        case ROTATE_Z -> {
            float newAngle = transformVec3f.rotation.getZ() + (increase ? ROTATION_INCREMENT : -ROTATION_INCREMENT);
            newAngle = MathHelper.wrapDegrees(newAngle - 180) + 180;
            transformVec3f.rotation.set(transformVec3f.rotation.getX(), transformVec3f.rotation.getY(), newAngle);
        }
        case TRANSLATE_X -> transformVec3f.translation
                .add(increase ? TRANSLATION_INCREMENT : -TRANSLATION_INCREMENT, 0, 0);
        case TRANSLATE_Y -> transformVec3f.translation
                .add(0, increase ? TRANSLATION_INCREMENT : -TRANSLATION_INCREMENT, 0);
        case TRANSLATE_Z -> transformVec3f.translation
                .add(0, 0, increase ? TRANSLATION_INCREMENT : -TRANSLATION_INCREMENT);
        case RESTORE_DEFAULT_ALL, RESTORE_DEFAULT -> {
            ItemModelFlexibleCamera.UpdateLink link = StartupClientOnly.modelBakeEventHandler.getItemOverrideLink();
            BakedModel savedModel = link.itemModelToOverride;
            if (savedModel != null) {  // not sure why this would ever be null, but it was (in a bug report), so just
                // check to make sure.
                link.itemModelToOverride = null;
                if (linkToHUDrenderer.selectedField == HUDTextRenderer.HUDInfoUpdateLink.SelectedField.RESTORE_DEFAULT) {
                    copySingleTransform(linkToHUDrenderer, savedModel, linkToHUDrenderer.selectedTransform);
                } else {
                    for (HUDTextRenderer.HUDInfoUpdateLink.TransformName transformName
                            : HUDTextRenderer.HUDInfoUpdateLink.TransformName.values()) {
                        copySingleTransform(linkToHUDrenderer, savedModel, transformName);
                    }
                }
            }
            link.itemModelToOverride = savedModel;
        }
        case PRINT -> {
            StringBuilder output = new StringBuilder();
            output.append("\n\"display\": {\n");
            printTransform(output, "thirdperson_righthand",
                    linkToHUDrenderer.itemCameraTransforms.thirdPersonRightHand);
            output.append(",\n");
            printTransform(output, "thirdperson_lefthand", linkToHUDrenderer.itemCameraTransforms.thirdPersonLeftHand);
            output.append(",\n");
            printTransform(output, "firstperson_righthand",
                    linkToHUDrenderer.itemCameraTransforms.firstPersonRightHand);
            output.append(",\n");
            printTransform(output, "firstperson_lefthand", linkToHUDrenderer.itemCameraTransforms.firstPersonLeftHand);
            output.append(",\n");
            printTransform(output, "gui", linkToHUDrenderer.itemCameraTransforms.gui);
            output.append(",\n");
            printTransform(output, "head", linkToHUDrenderer.itemCameraTransforms.head);
            output.append(",\n");
            printTransform(output, "fixed", linkToHUDrenderer.itemCameraTransforms.fixed);
            output.append(",\n");
            printTransform(output, "ground", linkToHUDrenderer.itemCameraTransforms.ground);
            output.append("\n}");
            LOGGER.info(output);
            LiteralText text = new LiteralText("\"display\" JSON section printed to console (LOGGER.info)...");
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(text);
        }
        }
    }

    // points to the appropriate transform based on which transform has been selected.
    private static Transformation getItemTransformRef(HUDTextRenderer.HUDInfoUpdateLink linkToHUDRenderer,
                                                      HUDTextRenderer.HUDInfoUpdateLink.TransformName transformName) {
        return switch (transformName) {
            case THIRD_LEFT -> linkToHUDRenderer.itemCameraTransforms.thirdPersonLeftHand;
            case THIRD_RIGHT -> linkToHUDRenderer.itemCameraTransforms.thirdPersonRightHand;
            case FIRST_LEFT -> linkToHUDRenderer.itemCameraTransforms.firstPersonLeftHand;
            case FIRST_RIGHT -> linkToHUDRenderer.itemCameraTransforms.firstPersonRightHand;
            case GUI -> linkToHUDRenderer.itemCameraTransforms.gui;
            case HEAD -> linkToHUDRenderer.itemCameraTransforms.head;
            case FIXED -> linkToHUDRenderer.itemCameraTransforms.fixed;
            case GROUND -> linkToHUDRenderer.itemCameraTransforms.ground;
        };
    }

    private void copySingleTransform(HUDTextRenderer.HUDInfoUpdateLink linkToHUDRenderer, BakedModel savedModel,
                                     HUDTextRenderer.HUDInfoUpdateLink.TransformName transformToBeCopied
    ) {
        Transformation transformVec3f = getItemTransformRef(linkToHUDRenderer, transformToBeCopied);
        ModelTransformation.Mode currentType = transformToBeCopied.getVanillaTransformType();
        Transformation transform = savedModel.getTransformation().getTransformation(currentType);
        copyTransforms(transform, transformVec3f);
    }

    private static void printTransform(StringBuilder output, String transformView, Transformation itemTransformVec3f) {
        output.append("    \"").append(transformView).append("\": {\n");
        output.append("        \"rotation\": [ ");
        output.append(String.format(Locale.US, "%.0f, ", itemTransformVec3f.rotation.getX()));
        output.append(String.format(Locale.US, "%.0f, ", itemTransformVec3f.rotation.getY()));
        output.append(String.format(Locale.US, "%.0f ],", itemTransformVec3f.rotation.getZ()));
        output.append("\n");

        final double TRANSLATE_MULTIPLIER = 1 / 0.0625;   // see ItemTransformVec3f::deserialize0()
        output.append("        \"translation\": [ ");
        output.append(String.format(Locale.US, "%.2f, ", itemTransformVec3f.translation.getX() * TRANSLATE_MULTIPLIER));
        output.append(String.format(Locale.US, "%.2f, ", itemTransformVec3f.translation.getY() * TRANSLATE_MULTIPLIER));
        output.append(String.format(Locale.US, "%.2f ],",
                itemTransformVec3f.translation.getZ() * TRANSLATE_MULTIPLIER));
        output.append("\n");
        output.append("        \"scale\": [ ");
        output.append(String.format(Locale.US, "%.2f, ", itemTransformVec3f.scale.getX()));
        output.append(String.format(Locale.US, "%.2f, ", itemTransformVec3f.scale.getY()));
        output.append(String.format(Locale.US, "%.2f ]", itemTransformVec3f.scale.getZ()));
        output.append("\n    }");
    }

    private static void copyTransforms(Transformation from, Transformation to) {
        to.translation.set(from.translation);
        to.scale.set(from.scale);
        to.rotation.set(from.rotation);
    }

    /**
     * Intercept arrow keys and handle repeats
     */
    public static class MenuKeyHandler {

        private long keyDownTimeTicks = 0;

        private ArrowKeys lastKey = ArrowKeys.NONE;

        private final KeyPressCallback keyPressCallback;

        public MenuKeyHandler(KeyPressCallback keyPressCallback) {
            this.keyPressCallback = keyPressCallback;
        }

        public void clientTick() {
            ArrowKeys keyPressed = ArrowKeys.NONE;
            if (isKeyDown(GLFW.GLFW_KEY_LEFT)) keyPressed = ArrowKeys.LEFT;
            if (isKeyDown(GLFW.GLFW_KEY_RIGHT)) keyPressed = ArrowKeys.RIGHT;
            if (isKeyDown(GLFW.GLFW_KEY_DOWN)) keyPressed = ArrowKeys.DOWN;
            if (isKeyDown(GLFW.GLFW_KEY_UP)) keyPressed = ArrowKeys.UP;

            if (keyPressed == ArrowKeys.NONE) {
                lastKey = ArrowKeys.NONE;
                return;
            }
            if (keyPressed != lastKey) {
                lastKey = keyPressed;
                keyDownTimeTicks = 0;
            } else {
                ++keyDownTimeTicks;
                final int INITIAL_PAUSE_TICKS = 10;  // wait 10 ticks before repeating
                if (keyDownTimeTicks < INITIAL_PAUSE_TICKS) return;
            }
            keyPressCallback.keyPressed(keyPressed);
        }

        static boolean isKeyDown(int key) {
            return GLFW.glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), key) == GLFW.GLFW_PRESS;
        }

        public enum ArrowKeys {NONE, UP, DOWN, LEFT, RIGHT}
    }

}
