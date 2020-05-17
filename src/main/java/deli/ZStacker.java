package deli;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class ZStacker {

  private TriangleIndex maxes;
  private TriangleIndex mins;
  public ZStacker(Collection<Triangle> tris){
    maxes = new TriangleIndex(tris, Triangle::maxZ);
    mins = new TriangleIndex(tris, Triangle::minZ);
  }

  public List<Triangle> orderedIncreasingMaxZ() {
    return maxes.getTriangles();
  }

  public Stream<Triangle> streamCrossing(double z){
    List<Triangle> ltMax = maxes.lte(z);
    List<Triangle> gtMin = mins.gte(z);

    // Compute the intersection
    return ltMax.stream().filter(gtMin::contains);
  }
}
