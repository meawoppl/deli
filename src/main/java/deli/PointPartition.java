package deli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class PointPartition {
  public final Vector3D[] above;
  public final Vector3D[] coplanar;
  public final Vector3D[] below;
  public static final double COPLANAR_EPS = 1e-6;

  public PointPartition(Vector3D[] above, Vector3D[] coplanar, Vector3D[] below) {
    this.above = above;
    this.coplanar = coplanar;
    this.below = below;
  }

  public static PointPartition ofTriangleAt(Triangle triangle, double z) {
    Map<Vector3D, Integer> parted = partition(triangle, z);
    List<Vector3D> above = new ArrayList<>();
    List<Vector3D> coplanar = new ArrayList<>();
    List<Vector3D> below = new ArrayList<>();

    parted.forEach(
        (v, i) -> {
          if (i == +1) above.add(v);
          if (i == 0) coplanar.add(v);
          if (i == -1) below.add(v);
        });

    final Vector3D[] v = new Vector3D[] {};
    return new PointPartition(above.toArray(v), coplanar.toArray(v), below.toArray(v));
  }

  private static int pointPartition(Vector3D p, double z) {
    if (p.getZ() > (z - COPLANAR_EPS) && p.getZ() < (z + COPLANAR_EPS)) return 0;
    else return p.getZ() < z ? -1 : 1;
  }

  public static Map<Vector3D, Integer> partition(Triangle triangle, double z) {
    Map<Vector3D, Integer> parted = new HashMap<>();
    for (Vector3D p : triangle.getPoints()) {
      parted.put(p, PointPartition.pointPartition(p, z));
    }
    return parted;
  } 
}
