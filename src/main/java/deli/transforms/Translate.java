package deli.transforms;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Translate implements PointTransformer {
  private final Vector3D translation;

  private Translate(Vector3D translation) {
    this.translation = translation;
  }

  public static Translate by(Vector3D translation) {
    return new Translate(translation);
  }

  @Override
  public Vector3D transform(Vector3D v) {
    return v.add(this.translation);
  }
}
