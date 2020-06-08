package deli.transforms;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public interface PointTransformer {
  Vector3D transform(Vector3D v);
}
