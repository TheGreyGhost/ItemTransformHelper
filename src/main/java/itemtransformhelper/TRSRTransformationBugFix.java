package itemtransformhelper;

// used to fix a bug in Forge TRSRTransformation in toXYZ() - present as of Forge 1.10.2-12.18.1.2011

import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public final class TRSRTransformationBugFix
{
  /*
   * Don't use this if you don't need to, conversion is lossy (second rotation component is lost).
   */
  @SideOnly(Side.CLIENT)
  public static ItemTransformVec3f toItemTransform(TRSRTransformation tr)
  {
    return new ItemTransformVec3f(toLwjgl(toXYZDegrees(tr.getLeftRot())), toLwjgl(tr.getTranslation()), toLwjgl(tr.getScale()));
  }

  public static Vector3f toXYZDegrees(Quat4f q)
  {
    Vector3f xyz = toXYZ(q);
    return new Vector3f((float)Math.toDegrees(xyz.x), (float)Math.toDegrees(xyz.y), (float)Math.toDegrees(xyz.z));
  }

  @SideOnly(Side.CLIENT)
  public static org.lwjgl.util.vector.Vector3f toLwjgl(Vector3f vec)
  {
    return new org.lwjgl.util.vector.Vector3f(vec.x, vec.y, vec.z);
  }

  public static Vector3f toXYZ(Quat4f q)
  {
    float w2 = q.w * q.w;
    float x2 = q.x * q.x;
    float y2 = q.y * q.y;
    float z2 = q.z * q.z;
    float l = w2 + x2 + y2 + z2;
    float siny = 2 * q.x * q.z + 2 * q.y * q.w;
    float y = (float)Math.asin(siny / l);
    if(Math.abs(siny) > .999f * l)
    {
      return new Vector3f(
              2 * (float)Math.atan2(q.x, q.w),
              y,
              0
      );
    }
    return new Vector3f(
            (float)Math.atan2(2 * q.x * q.w - 2 * q.z * q.y, (w2 - y2) - (x2 - z2)),
            y,
            (float)Math.atan2(2 * q.z * q.w - 2 * q.x * q.y, (w2 - y2) + (x2 - z2))
    );
  }
}