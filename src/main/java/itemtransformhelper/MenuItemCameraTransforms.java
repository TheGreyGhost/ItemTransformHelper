package itemtransformhelper;

import java.util.Locale;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Keyboard;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

/**
 * The menu used to select and alter the different parts of the ItemCameraTransform for the currently selected item.
 * The menu state is rendered on the screen by HUDtextRenderer.
 * The class registers its components on the Forge and FML event buses.
 * Created by TheGreyGhost on 22/01/15.
 */
@SuppressWarnings("deprecation")
public class MenuItemCameraTransforms
{
  public MenuItemCameraTransforms()
  {
    linkToHUDrenderer = new HUDtextRenderer.HUDinfoUpdateLink();
    MinecraftForge.EVENT_BUS.register(new HUDtextRenderer(linkToHUDrenderer));
    menuKeyHandler = new MenuKeyHandler(this.new KeyPressCallback());
    FMLCommonHandler.instance().bus().register(menuKeyHandler);
  }

  /**
   * get the current ItemCameraTransforms
   * @return the transform
   */
  public ItemCameraTransforms getItemCameraTransforms() {return linkToHUDrenderer.itemCameraTransforms;}

  /**
   * turn menu on or off
   * @param visible true = make visible
   */
  public void changeMenuVisible(boolean visible) {linkToHUDrenderer.menuVisible = visible;}

  public class KeyPressCallback
  {
    void keyPressed(MenuKeyHandler.ArrowKeys whichKey)
    {
      if (!linkToHUDrenderer.menuVisible) return;

      switch (whichKey) {
        case DOWN: {
          linkToHUDrenderer.selectedField = linkToHUDrenderer.selectedField.getNextField();
          break;
        }
        case UP: {
          linkToHUDrenderer.selectedField = linkToHUDrenderer.selectedField.getPreviousField();
          break;
        }
        case RIGHT:
        case LEFT: {
          alterField(whichKey == MenuKeyHandler.ArrowKeys.RIGHT);
          break;
        }
        case NONE:
      }
    }
  }

  private void alterField(boolean increase)
  {
    ItemTransformVec3f transformVec3f = null;
    switch (linkToHUDrenderer.selectedTransform) {
      case THIRD_LEFT: {transformVec3f = linkToHUDrenderer.itemCameraTransforms.thirdperson_left; break;}
      case THIRD_RIGHT: {transformVec3f = linkToHUDrenderer.itemCameraTransforms.thirdperson_right; break;}
      case FIRST_LEFT: {transformVec3f = linkToHUDrenderer.itemCameraTransforms.firstperson_left; break;}
      case FIRST_RIGHT: {transformVec3f = linkToHUDrenderer.itemCameraTransforms.firstperson_right; break;}
      case GUI: {transformVec3f = linkToHUDrenderer.itemCameraTransforms.gui; break;}
      case HEAD: {transformVec3f = linkToHUDrenderer.itemCameraTransforms.head; break;}
      case FIXED: {transformVec3f = linkToHUDrenderer.itemCameraTransforms.fixed; break;}
      case GROUND: {transformVec3f = linkToHUDrenderer.itemCameraTransforms.ground; break;}
    }
    if (transformVec3f == null) return; // should never happen

    final float SCALE_INCREMENT = 0.01F;
    final float ROTATION_INCREMENT = 2F;
    final float TRANSLATION_INCREMENT = 0.25F * 0.0625F; // 1/4 of a block, with multiplier from ItemTransformVec3f::deserialize0()
    switch (linkToHUDrenderer.selectedField) {
      case TRANSFORM: {
        linkToHUDrenderer.selectedTransform = increase ? linkToHUDrenderer.selectedTransform.getNext()
                : linkToHUDrenderer.selectedTransform.getPrevious();
        break;
      }
      case SCALE_X: {
        transformVec3f.scale.setX(transformVec3f.scale.getX() + (increase ? SCALE_INCREMENT : -SCALE_INCREMENT));
        break;
      }
      case SCALE_Y: {
        transformVec3f.scale.setY(transformVec3f.scale.getY() + (increase ? SCALE_INCREMENT : -SCALE_INCREMENT));
        break;
      }
      case SCALE_Z: {
        transformVec3f.scale.setZ(transformVec3f.scale.getZ() + (increase ? SCALE_INCREMENT : -SCALE_INCREMENT));
        break;
      }
      case ROTATE_X: {
        float newAngle = transformVec3f.rotation.getX() + (increase ? ROTATION_INCREMENT : -ROTATION_INCREMENT);
        newAngle = MathHelper.wrapDegrees(newAngle - 180) + 180;
        transformVec3f.rotation.setX(newAngle);
        break;
      }
      case ROTATE_Y: {
        float newAngle = transformVec3f.rotation.getY() + (increase ? ROTATION_INCREMENT : -ROTATION_INCREMENT);
        newAngle = MathHelper.wrapDegrees(newAngle - 180) + 180;
        transformVec3f.rotation.setY(newAngle);
        break;
      }
      case ROTATE_Z: {
        float newAngle = transformVec3f.rotation.getZ() + (increase ? ROTATION_INCREMENT : -ROTATION_INCREMENT);
        newAngle = MathHelper.wrapDegrees(newAngle - 180) + 180;
        transformVec3f.rotation.setZ(newAngle);
        break;
      }
      case TRANSLATE_X: {
        transformVec3f.translation.setX(transformVec3f.translation.getX() + (increase ? TRANSLATION_INCREMENT : -TRANSLATION_INCREMENT));
        break;
      }
      case TRANSLATE_Y: {
        transformVec3f.translation.setY(transformVec3f.translation.getY() + (increase ? TRANSLATION_INCREMENT : -TRANSLATION_INCREMENT));
        break;
      }
      case TRANSLATE_Z: {
        transformVec3f.translation.setZ(transformVec3f.translation.getZ() + (increase ? TRANSLATION_INCREMENT : -TRANSLATION_INCREMENT));
        break;
      }
      case RESTORE_DEFAULT: {
        ItemModelFlexibleCamera.UpdateLink link = StartupClientOnly.modelBakeEventHandler.getItemOverrideLink();
        IBakedModel savedModel = link.itemModelToOverride;
        if (savedModel != null) {  // not sure why this would ever be null, but it was (in a bug report), so just check to make sure.
          link.itemModelToOverride = null;
          if (savedModel instanceof IPerspectiveAwareModel) {  // IPerspectiveAware just have identity matrix for getItemCameraTransforms
            IPerspectiveAwareModel savedModelPA = (IPerspectiveAwareModel)savedModel;
            ItemCameraTransforms.TransformType currentType = linkToHUDrenderer.selectedTransform.getVanillaTransformType();
            Pair<? extends IBakedModel, Matrix4f> modelAndMatrix = savedModelPA.handlePerspective(currentType);
            TRSRTransformation tr = new TRSRTransformation(modelAndMatrix.getRight());
//            TRSRTransformation.TranslationRotationScale trs = tr.toItemTransform();
//            ItemTransformVec3f newTransform = new ItemTransformVec3f(trs.rotationLWJGL(), trs.translationLWJGL(), trs.scaleLWJGL());
//            ItemTransformVec3f newTransform = new ItemTransformVec3f(trs.rotationLWJGL(), trs.translationLWJGL(), trs.scaleLWJGL());
//            TRSRTransformation newTransformation = tr.blockCornerToCenter(tr);
            ItemTransformVec3f newItemTransform = TRSRTransformationBugFix.toItemTransform(tr);

            copyTransforms(newItemTransform, transformVec3f);

            TRSRTransformation tr1 = TRSRTransformation.blockCenterToCorner(tr); //todo remove debugs
            ItemTransformVec3f newItemTransform1 = tr1.toItemTransform();

            TRSRTransformation tr2 = TRSRTransformation.blockCornerToCenter(tr);
            ItemTransformVec3f newItemTransform2 = tr2.toItemTransform();

            ItemTransformVec3f itemTransform3 = tr2.toItemTransform();
            itemTransform3.rotation.set(75.0F, 45.0F, 0.0F);
            itemTransform3.translation.set(0.0F, 0.15625F, 0.0F);
            itemTransform3.scale.set(0.375F, 0.375F, 0.375F);
            TRSRTransformation tr3 = new TRSRTransformation(itemTransform3);
            ItemTransformVec3f itemTransform3rev = TRSRTransformationBugFix.toItemTransform(tr3);

            ItemTransformVec3f itemTransform4 = tr2.toItemTransform();
            itemTransform4.rotation.set(75.0F, 45.0F, 0.0F);
            itemTransform4.translation.set(0.0F, 0.0F, 0.0F);
            itemTransform4.scale.set(1.0F, 1.0F, 1.0F);
            TRSRTransformation tr4 = new TRSRTransformation(itemTransform4);
            ItemTransformVec3f itemTransform4rev = TRSRTransformationBugFix.toItemTransform(tr4);

            ItemTransformVec3f itemTransform5 = tr2.toItemTransform();
            itemTransform5.rotation.set(1.0F, 0.0F, 0.0F);
            itemTransform5.translation.set(0.0F, 0.0F, 0.0F);
            itemTransform5.scale.set(1.0F, 1.0F, 1.0F);
            TRSRTransformation tr5 = new TRSRTransformation(itemTransform5);
            ItemTransformVec3f itemTransform5rev = TRSRTransformationBugFix.toItemTransform(tr5);

            ItemTransformVec3f itemTransform6 = tr2.toItemTransform();
            itemTransform6.rotation.set(0.0F, 1.0F, 0.0F);
            itemTransform6.translation.set(0.0F, 0.0F, 0.0F);
            itemTransform6.scale.set(1.0F, 1.0F, 1.0F);
            TRSRTransformation tr6 = new TRSRTransformation(itemTransform6);
            ItemTransformVec3f itemTransform6rev = TRSRTransformationBugFix.toItemTransform(tr6);

            ItemTransformVec3f itemTransform7 = tr2.toItemTransform();
            itemTransform7.rotation.set(0.0F, 0.0F, 1.0F);
            itemTransform7.translation.set(0.0F, 0.0F, 0.0F);
            itemTransform7.scale.set(1.0F, 1.0F, 1.0F);
            TRSRTransformation tr7 = new TRSRTransformation(itemTransform7);
            ItemTransformVec3f itemTransform7rev = TRSRTransformationBugFix.toItemTransform(tr7);


            for (int a = -1; a <= 1; ++a) {
              for (int b = -1; b <= 1; ++b) {
                for (int c = -1; c <= 1; ++c) {
                  itemTransform7.rotation.set(a * 30, b * 45, c * 90);
                  itemTransform7.translation.set(0.0F, 0.0F, 0.0F);
                  itemTransform7.scale.set(1.0F, 1.0F, 1.0F);
                  tr7 = new TRSRTransformation(itemTransform7);
                  itemTransform7rev = TRSRTransformationBugFix.toItemTransform(tr7);
                  System.out.format("[%f, %f, %f] -> [%f, %f, %f]\n",
                                    itemTransform7.rotation.x, itemTransform7.rotation.y, itemTransform7.rotation.z,
                                    itemTransform7rev.rotation.x, itemTransform7rev.rotation.y, itemTransform7rev.rotation.z);
                }
              }
            }








          } else { // not IPerspectiveAwareModel
            ItemCameraTransforms originalTransforms = savedModel.getItemCameraTransforms();
            copyNonPerspectiveAware(originalTransforms, transformVec3f, linkToHUDrenderer.selectedTransform);
          }
          link.itemModelToOverride = savedModel;
        }
        break;
      }
      case PRINT: {
        StringBuilder output = new StringBuilder();
        output.append("\n\"display\": {\n");
        printTransform(output, "thirdperson_righthand", linkToHUDrenderer.itemCameraTransforms.thirdperson_right);
        output.append(",\n");
        printTransform(output, "thirdperson_lefthand", linkToHUDrenderer.itemCameraTransforms.thirdperson_left);
        output.append(",\n");
        printTransform(output, "firstperson_righthand", linkToHUDrenderer.itemCameraTransforms.firstperson_right);
        output.append(",\n");
        printTransform(output, "firstperson_lefthand", linkToHUDrenderer.itemCameraTransforms.firstperson_left);
        output.append(",\n");
        printTransform(output, "gui", linkToHUDrenderer.itemCameraTransforms.gui);
        output.append(",\n");
        printTransform(output, "head", linkToHUDrenderer.itemCameraTransforms.head);
        output.append("\n");
        printTransform(output, "fixed", linkToHUDrenderer.itemCameraTransforms.fixed);
        output.append("\n");
        printTransform(output, "ground", linkToHUDrenderer.itemCameraTransforms.ground);
        output.append("\n}");
        System.out.println(output);
        TextComponentString text = new TextComponentString("                   \"display\" JSON section printed to console...");
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(text);
        break;
      }
    }
  }

  private static void printTransform(StringBuilder output, String transformView, ItemTransformVec3f itemTransformVec3f) {
    output.append("    \"" + transformView + "\": {\n");
    output.append("        \"rotation\": [ ");
    output.append(String.format(Locale.US, "%.0f, ", itemTransformVec3f.rotation.getX()));
    output.append(String.format(Locale.US, "%.0f, ", itemTransformVec3f.rotation.getY()));
    output.append(String.format(Locale.US, "%.0f ],", itemTransformVec3f.rotation.getZ()));
    output.append("\n");

    final double TRANSLATE_MULTIPLIER = 1/ 0.0625;   // see ItemTransformVec3f::deserialize0()
    output.append("        \"translation\": [ ");
    output.append(String.format(Locale.US, "%.2f, ", itemTransformVec3f.translation.getX() * TRANSLATE_MULTIPLIER));
    output.append(String.format(Locale.US, "%.2f, ", itemTransformVec3f.translation.getY() * TRANSLATE_MULTIPLIER));
    output.append(String.format(Locale.US, "%.2f ],", itemTransformVec3f.translation.getZ() * TRANSLATE_MULTIPLIER));
    output.append("\n");
    output.append("        \"scale\": [ ");
    output.append(String.format(Locale.US, "%.2f, ", itemTransformVec3f.scale.getX()));
    output.append(String.format(Locale.US, "%.2f, ", itemTransformVec3f.scale.getY()));
    output.append(String.format(Locale.US, "%.2f ]", itemTransformVec3f.scale.getZ()));
    output.append("\n    }");
  }

  private static void copyTransforms(ItemTransformVec3f from, ItemTransformVec3f to)
  {
    to.translation.setX(from.translation.getX());
    to.scale.setX(from.scale.getX());
    to.rotation.setX(from.rotation.getX());
    to.translation.setY(from.translation.getY());
    to.scale.setY(from.scale.getY());
    to.rotation.setY(from.rotation.getY());
    to.translation.setZ(from.translation.getZ());
    to.scale.setZ(from.scale.getZ());
    to.rotation.setZ(from.rotation.getZ());
  }

  private void copyNonPerspectiveAware(ItemCameraTransforms copyFrom,
                                       ItemTransformVec3f copyTo,
                                       HUDtextRenderer.HUDinfoUpdateLink.TransformName whichViewSelected)
  {
      switch (whichViewSelected) {
        case THIRD_LEFT: {
              copyTransforms(copyFrom.thirdperson_left, copyTo);
              break;
          }
        case THIRD_RIGHT: {
          copyTransforms(copyFrom.thirdperson_right, copyTo);
          break;
        }
        case FIRST_RIGHT: {
              copyTransforms(copyFrom.firstperson_right, copyTo);
              break;
          }
        case FIRST_LEFT: {
          copyTransforms(copyFrom.firstperson_left, copyTo);
          break;
        }
          case GUI: {
              copyTransforms(copyFrom.gui, copyTo);
              break;
          }
          case HEAD: {
              copyTransforms(copyFrom.head, copyTo);
              break;
          }
          case FIXED: {
            copyTransforms(copyFrom.fixed, copyTo);
            break;
          }
          case GROUND: {
            copyTransforms(copyFrom.ground, copyTo);
            break;
          }
      }
  }


  private HUDtextRenderer.HUDinfoUpdateLink linkToHUDrenderer;
  private MenuKeyHandler menuKeyHandler;

  /**
   * Intercept arrow keys and handle repeats
   */
  public static class MenuKeyHandler
  {
    public MenuKeyHandler(KeyPressCallback i_keyPressCallback)
    {
      keyPressCallback = i_keyPressCallback;
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event)
    {
      if (event.phase != TickEvent.Phase.START) {
        return;
      }

      ArrowKeys keyPressed = ArrowKeys.NONE;
      if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) keyPressed = ArrowKeys.LEFT;
      if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) keyPressed = ArrowKeys.RIGHT;
      if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) keyPressed = ArrowKeys.DOWN;
      if (Keyboard.isKeyDown(Keyboard.KEY_UP)) keyPressed = ArrowKeys.UP;

      if (keyPressed == ArrowKeys.NONE) {
        lastKey = keyPressed;
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

    public enum ArrowKeys {NONE, UP, DOWN, LEFT, RIGHT}
    private long keyDownTimeTicks = 0;
    private ArrowKeys lastKey = ArrowKeys.NONE;
    private KeyPressCallback keyPressCallback;
  }
}
