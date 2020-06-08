package deli.transforms;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Scale implements PointTransformer {
  private final double scalar;

  private Scale(double scalar) {
    this.scalar = scalar;
  }

  public static Scale by(double scalar) {
    return new Scale(scalar);
  }

  @Override
  public Vector3D transform(Vector3D v) {
    return v.scalarMultiply(this.scalar);
  }
}
