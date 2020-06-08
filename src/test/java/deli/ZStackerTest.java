package deli;

import static org.junit.Assert.*;

import com.google.common.collect.Lists;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ZStackerTest extends Assertions {
  @Test
  public void streamCrossing() {
    Triangle a = new Triangle(Vector3D.PLUS_I, Vector3D.PLUS_J, Vector3D.PLUS_K);
    Triangle c = new Triangle(Vector3D.ZERO, Vector3D.ZERO, Vector3D.ZERO);
    Triangle b = new Triangle(Vector3D.MINUS_I, Vector3D.MINUS_J, Vector3D.MINUS_K);

    ZStacker stacker = new ZStacker(Lists.newArrayList(a, b, c));

    assertThat(stacker.streamCrossing(1)).containsExactly(a);
    assertThat(stacker.streamCrossing(-1)).containsExactly(b);

    assertThat(stacker.streamCrossing(0)).containsExactlyInAnyOrder(a, b, c);
  }

  @Test
  public void testOrdering() {
    Triangle a = new Triangle(Vector3D.PLUS_I, Vector3D.PLUS_J, Vector3D.PLUS_K);
    Triangle c = new Triangle(Vector3D.ZERO, Vector3D.ZERO, Vector3D.ZERO);
    Triangle b = new Triangle(Vector3D.MINUS_I, Vector3D.MINUS_J, Vector3D.MINUS_K);

    ZStacker stacker = new ZStacker(Lists.newArrayList(a, b, c));

    System.out.print(stacker.orderedIncreasingMaxZ());
  }
}
