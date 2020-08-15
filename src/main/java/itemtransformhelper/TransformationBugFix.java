package itemtransformhelper;

// used to fix a bug in Forge TransformationMatrix in toXYZ() - present as of Forge 1.10.2-12.18.1.2011


import net.minecraft.client.renderer.model.ItemTransformVec3f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class TransformationBugFix {
    /*
     * Don't use this if you don't need to, conversion is lossy (second rotation component is lost).
     */
    @OnlyIn(Dist.CLIENT)
    public static ItemTransformVec3f toItemTransform(TransformationMatrix tr) {
        return new ItemTransformVec3f(toXYZDegrees(tr.getRotationLeft()), tr.getTranslation(), tr.getScale());
    }

    public static Vector3f toXYZDegrees(Quaternion q) {
        Vector3f xyz = toXYZ(q);
        return new Vector3f((float) Math.toDegrees(xyz.getX()), (float) Math.toDegrees(xyz.getY()), (float) Math.toDegrees(xyz.getZ()));
    }

    public static Vector3f toXYZ(Quaternion q) {
        float w2 = q.getW() * q.getW();
        float x2 = q.getX() * q.getX();
        float y2 = q.getY() * q.getY();
        float z2 = q.getZ() * q.getZ();
        float l = w2 + x2 + y2 + z2;
        float siny = 2 * q.getX() * q.getZ() + 2 * q.getY() * q.getW();
        float y = (float) Math.asin(siny / l);
        if (Math.abs(siny) > .999f * l) {
            return new Vector3f(
                    2 * (float) Math.atan2(q.getX(), q.getW()),
                    y,
                    0
            );
        }
        return new Vector3f(
                (float) Math.atan2(2 * q.getX() * q.getW() - 2 * q.getZ() * q.getY(), (w2 - y2) - (x2 - z2)),
                y,
                (float) Math.atan2(2 * q.getZ() * q.getW() - 2 * q.getX() * q.getY(), (w2 - y2) + (x2 - z2))
        );
    }
}