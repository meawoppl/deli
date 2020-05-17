package deli;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class TriangleTest extends Assertions {

  @Test
  public void testMinMaxZ() {
    Triangle t = new Triangle(Vector3D.ZERO, Vector3D.PLUS_K, Vector3D.MINUS_K);
    assertThat(t.maxZ()).isEqualTo(1);
    assertThat(t.minZ()).isEqualTo(-1);
  }

  @Test
  public void testIsDegenerate() {
    {
      Triangle t = new Triangle(Vector3D.ZERO, Vector3D.PLUS_K, Vector3D.MINUS_K);
      assertThat(t.isDegenerate()).isFalse();
    }

    {
      Triangle t = new Triangle(Vector3D.ZERO, Vector3D.ZERO, Vector3D.MINUS_K);
      assertThat(t.isDegenerate()).isTrue();
    }

    {
      Triangle t = new Triangle(Vector3D.ZERO, Vector3D.PLUS_K, Vector3D.PLUS_K);
      assertThat(t.isDegenerate()).isTrue();
    }
  }

  @Test
  public void testNormalIntercept() throws Exception {
    Triangle t = new Triangle(Vector3D.ZERO, Vector3D.PLUS_I, Vector3D.PLUS_J);
    assertThat(t.getNormal()).isEqualTo(Vector3D.PLUS_K);
    assertThat(t.getIntercept()).isEqualTo(0);
  }
}