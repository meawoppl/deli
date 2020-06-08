package deli;

import java.util.List;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Test;

public class TriangleIndexTest extends Assertions {
  private static Triangle a = new Triangle(Vector3D.PLUS_K, Vector3D.PLUS_K, Vector3D.PLUS_K);
  private static Triangle b = new Triangle(Vector3D.ZERO, Vector3D.ZERO, Vector3D.ZERO);
  private static Triangle c = new Triangle(Vector3D.MINUS_K, Vector3D.MINUS_K, Vector3D.MINUS_K);
  private static List<Triangle> TEST_TRIPLET = Lists.newArrayList(a, b, c);

  @Test
  public void indexIsSequential() {
    TriangleIndex index = new TriangleIndex(TEST_TRIPLET, Triangle::minZ);

    assertThat(index.getIndex()).containsExactly(-1.0, 0.0, 1.0);
  }

  @Test
  public void testBetween() {
    TriangleIndex index = new TriangleIndex(TEST_TRIPLET, Triangle::minZ);

    // Easy/wide bounds
    assertThat(index.between(-2, 2)).containsExactly(c, b, a);

    // One triangle at each edge bound
    assertThat(index.between(-1, 1)).containsExactly(c, b, a);

    // Mid bound from both sides
    assertThat(index.between(-1, 0)).containsExactly(c, b);
    assertThat(index.between(0, 1)).containsExactly(b, a);

    // Bound that includes only 1 point
    assertThat(index.between(0, 0)).containsExactly(b);
    assertThat(index.between(1, 1)).containsExactly(a);
    assertThat(index.between(-1, -1)).containsExactly(c);

    // Bound that contains no points
    assertThat(index.between(0.1, 0.1)).isEmpty();
    assertThat(index.between(-0.1, -0.1)).isEmpty();
    assertThat(index.between(-10, -7)).isEmpty();
  }

  @Test
  public void testLessThan() {
    TriangleIndex index = new TriangleIndex(TEST_TRIPLET, Triangle::minZ);

    assertThat(index.lt(-1)).isEmpty();
    assertThat(index.lt(-0.9)).containsExactly(c);
  }

  @Test
  public void testGreaterThan() {
    TriangleIndex index = new TriangleIndex(TEST_TRIPLET, Triangle::minZ);
    assertThat(index.gt(-1)).containsExactly(b, a);
  }
}
