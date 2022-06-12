package itemtransformhelper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;

/**
 * User: The Grey Ghost
 * Date: 20/01/2015
 * Class to draw the menu on the screen
 */
public class HUDTextRenderer {

    private static final HUDInfoUpdateLink.SelectedField NOT_SELECTABLE = null;
    private static final int MED_GRAY_HALF_TRANSPARENT = 0x6FAFAFB0;
    private static final int GREEN_HALF_TRANSPARENT = 0x6F00FF00;
    private static final int LIGHT_GRAY = 0xE0E0E0;
    private static final int BLACK = 0x000000;

    private final HUDInfoUpdateLink hudInfoUpdateLink;

    /**
     * Create the HUDTextRenderer; displayHUDText needs to be called on RenderGameOverlayEvent.Text
     *
     * @param hudInfoUpdateLink the menu state information needed to draw the Heads Up Display
     */
    public HUDTextRenderer(HUDInfoUpdateLink hudInfoUpdateLink) {
        this.hudInfoUpdateLink = hudInfoUpdateLink;
    }

    /**
     * Draw the Head Up Display menu on screen.
     * The information is taken from the hudInfoUpdateLink which is updated by other classes.
     */
    public void displayHUDText(PoseStack poseStack) {
        if (hudInfoUpdateLink == null || !hudInfoUpdateLink.menuVisible || hudInfoUpdateLink.itemCameraTransforms == null)
            return;
        ArrayList<String> displayText = new ArrayList<>();
        ArrayList<HUDInfoUpdateLink.SelectedField> selectableField = new ArrayList<>();

        displayText.add("======");
        selectableField.add(NOT_SELECTABLE);
        displayText.add("VIEW  ");
        selectableField.add(NOT_SELECTABLE);
        ItemTransform transformation;

        switch (hudInfoUpdateLink.selectedTransform) {
        case THIRD_LEFT -> {
            displayText.add("3rd-L");
            transformation = hudInfoUpdateLink.itemCameraTransforms.thirdPersonLeftHand;
        }
        case THIRD_RIGHT -> {
            displayText.add("3rd-R");
            transformation = hudInfoUpdateLink.itemCameraTransforms.thirdPersonRightHand;
        }
        case FIRST_LEFT -> {
            displayText.add("1st-L");
            transformation = hudInfoUpdateLink.itemCameraTransforms.firstPersonLeftHand;
        }
        case FIRST_RIGHT -> {
            displayText.add("1st-R");
            transformation = hudInfoUpdateLink.itemCameraTransforms.firstPersonRightHand;
        }
        case GUI -> {
            displayText.add("gui");
            transformation = hudInfoUpdateLink.itemCameraTransforms.gui;
        }
        case HEAD -> {
            displayText.add("head");
            transformation = hudInfoUpdateLink.itemCameraTransforms.head;
        }
        case FIXED -> {
            displayText.add("fixed");
            transformation = hudInfoUpdateLink.itemCameraTransforms.fixed;
        }
        case GROUND -> {
            displayText.add("grnd");
            transformation = hudInfoUpdateLink.itemCameraTransforms.ground;
        }
        default ->
                throw new IllegalArgumentException("Unknown cameraTransformType:" + hudInfoUpdateLink.selectedTransform);
        }
        selectableField.add(HUDInfoUpdateLink.SelectedField.TRANSFORM);

        displayText.add("======");
        selectableField.add(NOT_SELECTABLE);
        displayText.add("SCALE");
        selectableField.add(NOT_SELECTABLE);
        displayText.add("X:" + String.format("%.2f", transformation.scale.x()));
        selectableField.add(HUDInfoUpdateLink.SelectedField.SCALE_X);
        displayText.add("Y:" + String.format("%.2f", transformation.scale.y()));
        selectableField.add(HUDInfoUpdateLink.SelectedField.SCALE_Y);
        displayText.add("Z:" + String.format("%.2f", transformation.scale.z()));
        selectableField.add(HUDInfoUpdateLink.SelectedField.SCALE_Z);

        displayText.add("======");
        selectableField.add(NOT_SELECTABLE);
        displayText.add("ROTATE");
        selectableField.add(NOT_SELECTABLE);
        displayText.add("X:" + String.format("%3.0f", transformation.rotation.x()));
        selectableField.add(HUDInfoUpdateLink.SelectedField.ROTATE_X);
        displayText.add("Y:" + String.format("%3.0f", transformation.rotation.y()));
        selectableField.add(HUDInfoUpdateLink.SelectedField.ROTATE_Y);
        displayText.add("Z:" + String.format("%3.0f", transformation.rotation.z()));
        selectableField.add(HUDInfoUpdateLink.SelectedField.ROTATE_Z);

        final double TRANSLATE_MULTIPLIER = 1 / 0.0625;   // see Transformation.Deserializer::deserialize
        displayText.add("======");
        selectableField.add(NOT_SELECTABLE);
        displayText.add("TRANSL");
        selectableField.add(NOT_SELECTABLE);
        displayText.add("X:" + String.format("%.2f", transformation.translation.x() * TRANSLATE_MULTIPLIER));
        selectableField.add(HUDInfoUpdateLink.SelectedField.TRANSLATE_X);
        displayText.add("Y:" + String.format("%.2f", transformation.translation.y() * TRANSLATE_MULTIPLIER));
        selectableField.add(HUDInfoUpdateLink.SelectedField.TRANSLATE_Y);
        displayText.add("Z:" + String.format("%.2f", transformation.translation.z() * TRANSLATE_MULTIPLIER));
        selectableField.add(HUDInfoUpdateLink.SelectedField.TRANSLATE_Z);

        displayText.add("======");
        selectableField.add(NOT_SELECTABLE);
        displayText.add("RESET");
        selectableField.add(HUDInfoUpdateLink.SelectedField.RESTORE_DEFAULT);
        displayText.add("RSTALL");
        selectableField.add(HUDInfoUpdateLink.SelectedField.RESTORE_DEFAULT_ALL);
        displayText.add("PRINT");
        selectableField.add(HUDInfoUpdateLink.SelectedField.PRINT);
        displayText.add("======");
        selectableField.add(NOT_SELECTABLE);

        Font textRenderer = Minecraft.getInstance().font;
        int yPos = 2;
        int xPos = 2;
        for (int i = 0; i < displayText.size(); ++i) {
            String msg = displayText.get(i);
            yPos += textRenderer.lineHeight;
            if (msg == null) continue;
            boolean fieldIsSelected = hudInfoUpdateLink.selectedField == selectableField.get(i);
            int highlightColour = fieldIsSelected ? GREEN_HALF_TRANSPARENT : MED_GRAY_HALF_TRANSPARENT;
            GuiComponent.fill(poseStack, xPos - 1, yPos - 1,
                    xPos + textRenderer.width(msg) + 1, yPos + textRenderer.lineHeight - 1, highlightColour);
            int stringColour = fieldIsSelected ? BLACK : LIGHT_GRAY;
            textRenderer.draw(poseStack, msg, xPos, yPos, stringColour);
        }
    }

    /**
     * Used to provide the information that the HUDTextRenderer needs to draw the menu
     */
    public static class HUDInfoUpdateLink {

        private static final Vector3f ROTATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
        private static final Vector3f TRANSLATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
        private static final Vector3f SCALE_DEFAULT = new Vector3f(1.0F, 1.0F, 1.0F);

        public ItemTransforms itemCameraTransforms;
        public SelectedField selectedField;
        public TransformName selectedTransform;
        public boolean menuVisible;

        public HUDInfoUpdateLink() {
            ItemTransform trThirdLeft = new ItemTransform(ROTATION_DEFAULT, TRANSLATION_DEFAULT, SCALE_DEFAULT);
            ItemTransform trThirdRight = new ItemTransform(ROTATION_DEFAULT, TRANSLATION_DEFAULT, SCALE_DEFAULT);
            ItemTransform trFirstLeft = new ItemTransform(ROTATION_DEFAULT, TRANSLATION_DEFAULT, SCALE_DEFAULT);
            ItemTransform trFirstRight = new ItemTransform(ROTATION_DEFAULT, TRANSLATION_DEFAULT, SCALE_DEFAULT);
            ItemTransform trHead = new ItemTransform(ROTATION_DEFAULT, TRANSLATION_DEFAULT, SCALE_DEFAULT);
            ItemTransform trGui = new ItemTransform(ROTATION_DEFAULT, TRANSLATION_DEFAULT, SCALE_DEFAULT);
            ItemTransform trGround = new ItemTransform(ROTATION_DEFAULT, TRANSLATION_DEFAULT, SCALE_DEFAULT);
            ItemTransform trFixed = new ItemTransform(ROTATION_DEFAULT, TRANSLATION_DEFAULT, SCALE_DEFAULT);
            itemCameraTransforms = new ItemTransforms(trThirdLeft, trThirdRight, trFirstLeft, trFirstRight,
                    trHead, trGui, trGround, trFixed);

            selectedField = SelectedField.TRANSFORM;
            selectedTransform = TransformName.FIRST_RIGHT;
            menuVisible = false;
        }

        public enum TransformName {

            THIRD_LEFT(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND),
            THIRD_RIGHT(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND),
            FIRST_LEFT(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND),
            FIRST_RIGHT(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND),
            HEAD(ItemTransforms.TransformType.HEAD),
            GUI(ItemTransforms.TransformType.GUI),
            GROUND(ItemTransforms.TransformType.GROUND),
            FIXED(ItemTransforms.TransformType.FIXED);

            public static final TransformName[] VALUES = TransformName.values();

            private final ItemTransforms.TransformType vanillaType;

            public TransformName getNext() {
                for (TransformName transformName : VALUES) {
                    if (transformName.ordinal() == this.ordinal() + 1) return transformName;
                }
                return THIRD_LEFT;
            }

            public TransformName getPrevious() {
                for (TransformName transformName : VALUES) {
                    if (transformName.ordinal() == this.ordinal() - 1) return transformName;
                }
                return FIXED;
            }

            public ItemTransforms.TransformType getVanillaTransformType() {
                return vanillaType;
            }

            TransformName(ItemTransforms.TransformType vanillaType) {
                this.vanillaType = vanillaType;
            }

        }

        public enum SelectedField {

            TRANSFORM(0),
            SCALE_X(1), SCALE_Y(2), SCALE_Z(3),
            ROTATE_X(4), ROTATE_Y(5), ROTATE_Z(6),
            TRANSLATE_X(7), TRANSLATE_Y(8), TRANSLATE_Z(9),
            RESTORE_DEFAULT(10), RESTORE_DEFAULT_ALL(11), PRINT(12);

            public static final SelectedField[] VALUES = SelectedField.values();
            private static final SelectedField FIRST_FIELD = TRANSFORM;
            private static final SelectedField LAST_FIELD = PRINT;

            public final int fieldIndex;

            SelectedField(int index) {
                fieldIndex = index;
            }

            public static SelectedField getFieldName(int indexToFind) {
                for (SelectedField checkField : VALUES) {
                    if (checkField.fieldIndex == indexToFind) return checkField;
                }
                return null;
            }

            public SelectedField getNextField() {
                SelectedField nextField = getFieldName(fieldIndex + 1);
                if (nextField == null) nextField = FIRST_FIELD;
                return nextField;
            }

            public SelectedField getPreviousField() {
                SelectedField previousField = getFieldName(fieldIndex - 1);
                if (previousField == null) previousField = LAST_FIELD;
                return previousField;
            }

        }

    }

}
