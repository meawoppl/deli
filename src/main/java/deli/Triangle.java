package deli;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Arrays;
import java.util.Objects;

public class Triangle {
  public static class DegenerateTriangle extends Exception {}

  private final Vector3D a;
  private final Vector3D b;
  private final Vector3D c;

  private final boolean degenerate;
  private final double[] normalizedForm;

  public Triangle(Vector3D ... points) {
    this.a = points[0];
    this.b = points[1];
    this.c = points[2];

    Vector3D v1 = b.subtract(a);
    Vector3D v2 = c.subtract(a);

    Vector3D cross = v1.crossProduct(v2);

    this.degenerate = cross.getNorm() == 0.0;
    if(degenerate) {
      normalizedForm = new double[]{};
    } else {
      Vector3D normed = cross.normalize();
      double i = this.a.dotProduct(normed);
      normalizedForm = new double[]{
              normed.getX(),
              normed.getY(),
              normed.getZ(),
              i
      };
    }
  }

  public Vector3D[] getPoints() {
    return new Vector3D[]{this.a, this.b, this.c};
  }

  public void throwIfDegenerate() throws DegenerateTriangle {
    if (degenerate){
      throw new DegenerateTriangle();
    }
  }

  public Vector3D getNormal() throws DegenerateTriangle {
    throwIfDegenerate();
    return new Vector3D(Arrays.copyOf(normalizedForm, 3));
  }

  public double getIntercept() throws DegenerateTriangle {
    throwIfDegenerate();
    return normalizedForm[3];
  }

  /**
   * Return true iff the triangle has no area.
   *
   * @return boolean
   */
  public boolean isDegenerate(){
    return this.a == this.b || this.b == this.c || this.c == this.a;
  }

  double minZ(){
    return Math.min(Math.min(a.getZ(), b.getZ()), c.getZ());
  }

  double maxZ() {
    return Math.max(Math.max(a.getZ(), b.getZ()), c.getZ());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Triangle: ");
    sb.append(this.a).append(" ");
    sb.append(this.b).append(" ");
    sb.append(this.c);
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Triangle triangle = (Triangle) o;
    return Objects.equals(a, triangle.a) &&
            Objects.equals(b, triangle.b) &&
            Objects.equals(c, triangle.c);
  }

  @Override
  public int hashCode() {
    return Objects.hash(a, b, c);
  }
}
