package deli;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.util.Pair;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public class TriangleSoup extends AbstractSet<Triangle> implements Set<Triangle>  {
  private final ImmutableSet<Triangle> triangles;

  public TriangleSoup(Collection<Triangle> triangles) {
    this.triangles = ImmutableSet.copyOf(triangles);
  }

  private Stream<Vector3D> pointStream(){
    return triangles.stream()
            .map(Triangle::getPoints)
            .map(Arrays::asList)
            .flatMap(Collection::stream);
  }

  public DoubleSummaryStatistics[] getPointStats(){
    DoubleSummaryStatistics x = new DoubleSummaryStatistics();
    DoubleSummaryStatistics y = new DoubleSummaryStatistics();
    DoubleSummaryStatistics z = new DoubleSummaryStatistics();


    pointStream().forEach(pt -> {
      x.accept(pt.getX());
      y.accept(pt.getX());
      z.accept(pt.getX());
    });

    return new DoubleSummaryStatistics[] {x, y, z};
  }

  public Pair<Vector3D, Vector3D> getBounds(){
    DoubleSummaryStatistics[] stats = getPointStats();

    double xMin = stats[0].getMin();
    double yMin = stats[1].getMin();
    double zMin = stats[2].getMin();

    double xMax = stats[0].getMax();
    double yMax = stats[1].getMax();
    double zMax = stats[2].getMax();

    return Pair.create(new Vector3D(xMin, yMin, zMin), new Vector3D(xMax, yMax, zMax));
  }

  // Methods to implement the set interface
  @Override
  public Iterator<Triangle> iterator() {
    return triangles.iterator();
  }

  @Override
  public int size() {
    return triangles.size();
  }
}
