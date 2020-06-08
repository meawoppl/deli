package deli;

import java.awt.Graphics2D;
import java.util.Collection;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Intersecter {
  private final Collection<Triangle> tris;

  public Intersecter(Collection<Triangle> tris) {
    this.tris = tris;
  }

  public Vector3D lerpDown(Vector3D above, Vector3D below, double z) {
    assert (above.getZ() >= z && below.getZ() <= z);

    double zTop = above.getZ();
    double zBottom = below.getZ();

    double nDim = (z - zBottom) / (zTop - zBottom);

    return above.scalarMultiply(nDim).add(below.scalarMultiply(1.0 - nDim));
  }

  public void vecsIntoGraphics(Vector3D pt1, Vector3D pt2, Graphics2D g) {
    int x1 = (int) Math.round(pt1.getX());
    int y1 = (int) Math.round(pt1.getY());

    int x2 = (int) Math.round(pt2.getX());
    int y2 = (int) Math.round(pt2.getY());

    g.drawLine(x1, y1, x2, y2);
  }

  public void drawPlaneIntersection(Graphics2D graphics, double z) {
    // Use the z stacker to get candidate triangles

    for (Triangle t : this.tris) {
      PointPartition parted = PointPartition.ofTriangleAt(t, z);

      /////////////////
      // Trivial Cases
      if (parted.above.length == 3 || parted.below.length == 3)
        // (all above/all below), we can ignore
        continue;

      //////////////////
      // Coplanar cases

      // One in slice plane
      if (parted.coplanar.length == 1) {
        // Remaining points both above or both below (do nothing)
        if (parted.above.length == 2 || parted.below.length == 2) continue;

        // One above, one below
        Vector3D interped = lerpDown(parted.above[0], parted.below[0], z);

        vecsIntoGraphics(interped, parted.coplanar[0], graphics);
        continue;
      }

      if (parted.coplanar.length == 2) {
        // draw a->b
        vecsIntoGraphics(parted.coplanar[0], parted.coplanar[1], graphics);
        continue;
      }

      if (parted.coplanar.length == 3) {
        // draw a->b
        vecsIntoGraphics(parted.coplanar[0], parted.coplanar[1], graphics);
        // draw b->c
        vecsIntoGraphics(parted.coplanar[1], parted.coplanar[2], graphics);
        // draw c->a
        vecsIntoGraphics(parted.coplanar[2], parted.coplanar[0], graphics);
        continue;
      }

      //////////////////
      // Spanning cases

      int index = 0;
      Vector3D[] inPlane = new Vector3D[2];

      for (Vector3D above : parted.above) {
        for (Vector3D below : parted.below) {
          inPlane[index] = lerpDown(above, below, z);
          index += 1;
        }
      }

      vecsIntoGraphics(inPlane[0], inPlane[1], graphics);
    }
  }
}
