package itemtransformhelper;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * User: The Grey Ghost
 * Date: 20/01/2015
 * Class to draw the menu on the screen
 *
 */
@SuppressWarnings("deprecation")
public class HUDtextRenderer
{
  /**
   * Create the HUDtextRenderer; caller needs to register this class on the forge event bus
   * @param i_HUDinfoUpdateLink the menu state information needed to draw the Heads Up Display
   */
  public HUDtextRenderer(HUDinfoUpdateLink i_HUDinfoUpdateLink)
  {
    huDinfoUpdateLink = i_HUDinfoUpdateLink;
  }

  /**
   * Draw the Head Up Display menu on screen.
   * The information is taken from the hudInfoUpdateLink which is updated by other classes.
   * @param event
   */
  @SubscribeEvent
  public void displayHUDtext(RenderGameOverlayEvent.Text event)
  {
    if (huDinfoUpdateLink == null || !huDinfoUpdateLink.menuVisible ||  huDinfoUpdateLink.itemCameraTransforms == null) return;
    ArrayList<String> displayText = new ArrayList<String>();
    ArrayList<HUDinfoUpdateLink.SelectedField> selectableField = new ArrayList<HUDinfoUpdateLink.SelectedField>();

    final HUDinfoUpdateLink.SelectedField NOT_SELECTABLE = null;

    displayText.add("======"); selectableField.add(NOT_SELECTABLE);
    displayText.add("VIEW  "); selectableField.add(NOT_SELECTABLE);
    ItemTransformVec3f transformVec3f = huDinfoUpdateLink.itemCameraTransforms.thirdperson_right;

    switch (huDinfoUpdateLink.selectedTransform) {
      case THIRD_LEFT: {displayText.add("3rd-L"); transformVec3f = huDinfoUpdateLink.itemCameraTransforms.thirdperson_left; break;}
      case THIRD_RIGHT: {displayText.add("3rd-R"); transformVec3f = huDinfoUpdateLink.itemCameraTransforms.thirdperson_right; break;}
      case FIRST_LEFT: {displayText.add("1st-L"); transformVec3f = huDinfoUpdateLink.itemCameraTransforms.firstperson_left; break;}
      case FIRST_RIGHT: {displayText.add("1st-R"); transformVec3f = huDinfoUpdateLink.itemCameraTransforms.firstperson_right; break;}
      case GUI: {displayText.add("gui"); transformVec3f = huDinfoUpdateLink.itemCameraTransforms.gui; break;}
      case HEAD: {displayText.add("head"); transformVec3f = huDinfoUpdateLink.itemCameraTransforms.head; break;}
      case FIXED: {displayText.add("fixed"); transformVec3f = huDinfoUpdateLink.itemCameraTransforms.fixed; break;}
      case GROUND: {displayText.add("grnd"); transformVec3f = huDinfoUpdateLink.itemCameraTransforms.ground; break;}
      default: {
        throw new IllegalArgumentException("Unknown cameraTransformType:" + huDinfoUpdateLink.selectedTransform);
      }
    }
    selectableField.add(HUDinfoUpdateLink.SelectedField.TRANSFORM);

    displayText.add("======"); selectableField.add(NOT_SELECTABLE);
    displayText.add("SCALE"); selectableField.add(NOT_SELECTABLE);
    displayText.add("X:" + String.format("%.2f", transformVec3f.scale.getX()));
    selectableField.add(HUDinfoUpdateLink.SelectedField.SCALE_X);
    displayText.add("Y:" + String.format("%.2f", transformVec3f.scale.getY()));
    selectableField.add(HUDinfoUpdateLink.SelectedField.SCALE_Y);
    displayText.add("Z:" + String.format("%.2f", transformVec3f.scale.getZ()));
    selectableField.add(HUDinfoUpdateLink.SelectedField.SCALE_Z);

    displayText.add("======"); selectableField.add(NOT_SELECTABLE);
    displayText.add("ROTATE"); selectableField.add(NOT_SELECTABLE);
    displayText.add("X:" + String.format("%3.0f", transformVec3f.rotation.getX()));
    selectableField.add(HUDinfoUpdateLink.SelectedField.ROTATE_X);
    displayText.add("Y:" + String.format("%3.0f", transformVec3f.rotation.getY()));
    selectableField.add(HUDinfoUpdateLink.SelectedField.ROTATE_Y);
    displayText.add("Z:" + String.format("%3.0f", transformVec3f.rotation.getZ()));
    selectableField.add(HUDinfoUpdateLink.SelectedField.ROTATE_Z);

    final double TRANSLATE_MULTIPLIER = 1/ 0.0625;   // see ItemTransformVec3f::deserialize0()
    displayText.add("======"); selectableField.add(NOT_SELECTABLE);
    displayText.add("TRANSL"); selectableField.add(NOT_SELECTABLE);
    displayText.add("X:" + String.format("%.2f", transformVec3f.translation.getX() * TRANSLATE_MULTIPLIER));
    selectableField.add(HUDinfoUpdateLink.SelectedField.TRANSLATE_X);
    displayText.add("Y:" + String.format("%.2f", transformVec3f.translation.getY() * TRANSLATE_MULTIPLIER));
    selectableField.add(HUDinfoUpdateLink.SelectedField.TRANSLATE_Y);
    displayText.add("Z:" + String.format("%.2f", transformVec3f.translation.getZ() * TRANSLATE_MULTIPLIER));
    selectableField.add(HUDinfoUpdateLink.SelectedField.TRANSLATE_Z);

    displayText.add("======"); selectableField.add(NOT_SELECTABLE);
    displayText.add("RESET"); selectableField.add(HUDinfoUpdateLink.SelectedField.RESTORE_DEFAULT);
    displayText.add("RSTALL"); selectableField.add(HUDinfoUpdateLink.SelectedField.RESTORE_DEFAULT_ALL);
    displayText.add("PRINT"); selectableField.add(HUDinfoUpdateLink.SelectedField.PRINT);
    displayText.add("======"); selectableField.add(NOT_SELECTABLE);

    FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
    int ypos = 2;
    int xpos = 2;
    for (int i = 0; i < displayText.size(); ++i) {
      String msg = displayText.get(i);
      ypos += fontRenderer.FONT_HEIGHT;
      if (msg == null) continue;
      final int MED_GRAY_HALF_TRANSPARENT = 0x6FAFAFB0;
      final int GREEN_HALF_TRANSPARENT = 0x6F00FF00;
      boolean fieldIsSelected = (huDinfoUpdateLink.selectedField == selectableField.get(i));
      int highlightColour = fieldIsSelected ? GREEN_HALF_TRANSPARENT : MED_GRAY_HALF_TRANSPARENT;
      drawRect(xpos - 1, ypos - 1, xpos + fontRenderer.getStringWidth(msg) + 1, ypos + fontRenderer.FONT_HEIGHT - 1, highlightColour);
      final int LIGHT_GRAY = 0xE0E0E0;
      final int BLACK = 0x000000;
      int stringColour = fieldIsSelected ? BLACK : LIGHT_GRAY;
      fontRenderer.drawString(msg, xpos, ypos, stringColour);
    }
  }

  /**
   * Used to provide the information that the HUDtextRenderer needs to draw the menu
   */
  public static class HUDinfoUpdateLink
  {
    public ItemCameraTransforms itemCameraTransforms;
    public SelectedField selectedField;
    public TransformName selectedTransform;
    public boolean menuVisible;

    public HUDinfoUpdateLink()
    {
      final Vector3f ROTATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
      final Vector3f TRANSLATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
      final Vector3f SCALE_DEFAULT = new Vector3f(1.0F, 1.0F, 1.0F);

      ItemTransformVec3f itvThirdLeft = new ItemTransformVec3f(new Vector3f(ROTATION_DEFAULT), new Vector3f(TRANSLATION_DEFAULT), new Vector3f(SCALE_DEFAULT));
      ItemTransformVec3f itvThirdRight = new ItemTransformVec3f(new Vector3f(ROTATION_DEFAULT), new Vector3f(TRANSLATION_DEFAULT), new Vector3f(SCALE_DEFAULT));
      ItemTransformVec3f itvFirstLeft = new ItemTransformVec3f(new Vector3f(ROTATION_DEFAULT), new Vector3f(TRANSLATION_DEFAULT), new Vector3f(SCALE_DEFAULT));
      ItemTransformVec3f itvFirstRight = new ItemTransformVec3f(new Vector3f(ROTATION_DEFAULT), new Vector3f(TRANSLATION_DEFAULT), new Vector3f(SCALE_DEFAULT));
      ItemTransformVec3f itvHead = new ItemTransformVec3f(new Vector3f(ROTATION_DEFAULT), new Vector3f(TRANSLATION_DEFAULT), new Vector3f(SCALE_DEFAULT));
      ItemTransformVec3f itvGui = new ItemTransformVec3f(new Vector3f(ROTATION_DEFAULT), new Vector3f(TRANSLATION_DEFAULT), new Vector3f(SCALE_DEFAULT));
      ItemTransformVec3f itvGround = new ItemTransformVec3f(new Vector3f(ROTATION_DEFAULT), new Vector3f(TRANSLATION_DEFAULT), new Vector3f(SCALE_DEFAULT));
      ItemTransformVec3f itvFixed = new ItemTransformVec3f(new Vector3f(ROTATION_DEFAULT), new Vector3f(TRANSLATION_DEFAULT), new Vector3f(SCALE_DEFAULT));
      itemCameraTransforms = new ItemCameraTransforms(itvThirdLeft, itvThirdRight, itvFirstLeft, itvFirstRight, itvHead, itvGui, itvGround, itvFixed);

      selectedField = SelectedField.TRANSFORM;
      selectedTransform = TransformName.FIRST_RIGHT;
      menuVisible = false;
    }

    public enum TransformName {
        THIRD_LEFT(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND),
        THIRD_RIGHT(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND),
        FIRST_LEFT(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND),
        FIRST_RIGHT(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND),
        HEAD(ItemCameraTransforms.TransformType.HEAD),
        GUI(ItemCameraTransforms.TransformType.GUI),
        GROUND(ItemCameraTransforms.TransformType.GROUND),
        FIXED(ItemCameraTransforms.TransformType.FIXED);

      public TransformName getNext()
      {
        for (TransformName transformName : TransformName.values()) {
          if (transformName.ordinal() == this.ordinal() + 1) return transformName;
        }
        return THIRD_LEFT;
      }
      public TransformName getPrevious()
      {
        for (TransformName transformName : TransformName.values()) {
          if (transformName.ordinal() == this.ordinal() - 1) return transformName;
        }
        return FIXED;
      }
      public ItemCameraTransforms.TransformType getVanillaTransformType() {
        return vanillaType;
      }

      TransformName(ItemCameraTransforms.TransformType i_type) {
        vanillaType = i_type;
      }
      private ItemCameraTransforms.TransformType vanillaType;
    }

    public enum SelectedField {
      TRANSFORM(0), SCALE_X(1), SCALE_Y(2), SCALE_Z(3), ROTATE_X(4), ROTATE_Y(5), ROTATE_Z(6), TRANSLATE_X(7),
      TRANSLATE_Y(8), TRANSLATE_Z(9), RESTORE_DEFAULT(10), RESTORE_DEFAULT_ALL(11), PRINT(12);

      private SelectedField(int index) {fieldIndex = index;}
      public final int fieldIndex;
      private static final SelectedField FIRST_FIELD = TRANSFORM;
      private static final SelectedField LAST_FIELD = PRINT;

      public static SelectedField getFieldName(int indexToFind)
      {
        for (SelectedField checkField : SelectedField.values()) {
          if (checkField.fieldIndex == indexToFind) return checkField;
        }
        return null;
      }
      public SelectedField getNextField()
      {
        SelectedField nextField = getFieldName(fieldIndex + 1);
        if (nextField == null) nextField = FIRST_FIELD;
        return nextField;
      }
      public SelectedField getPreviousField()
      {
        SelectedField previousField = getFieldName(fieldIndex - 1);
        if (previousField == null) previousField = LAST_FIELD;
        return previousField;
      }
    }

  }

  private HUDinfoUpdateLink huDinfoUpdateLink;

  // copied straight from vanilla GuiIngameForge
  private static void drawRect(int left, int top, int right, int bottom, int color)
  {
    int j1;

    if (left < right)
    {
      j1 = left;
      left = right;
      right = j1;
    }

    if (top < bottom)
    {
      j1 = top;
      top = bottom;
      bottom = j1;
    }

    float f3 = (float)(color >> 24 & 255) / 255.0F;
    float f = (float)(color >> 16 & 255) / 255.0F;
    float f1 = (float)(color >> 8 & 255) / 255.0F;
    float f2 = (float)(color & 255) / 255.0F;
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder vertexBuffer = tessellator.getBuffer();
    GlStateManager.enableBlend();
    GlStateManager.disableTexture2D();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    GlStateManager.color(f, f1, f2, f3);

    vertexBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
    vertexBuffer.pos((double) left, (double) bottom, 0.0D).endVertex();
    vertexBuffer.pos((double) right, (double) bottom, 0.0D).endVertex();
    vertexBuffer.pos((double) right, (double) top, 0.0D).endVertex();
    vertexBuffer.pos((double) left, (double) top, 0.0D).endVertex();
    tessellator.draw();
    GlStateManager.enableTexture2D();
    GlStateManager.disableBlend();
  }


}
