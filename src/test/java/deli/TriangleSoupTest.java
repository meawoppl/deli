package deli;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TriangleSoupTest extends Assertions {

  @Test
  public void testStatsOnUnitCube() {
    StlReader reader = new StlReader(new StlReaderTest().loadTestCube());
    TriangleSoup soup = new TriangleSoup(reader.getTriangles());
    Vector3D lower = soup.getBounds().getFirst();
    Vector3D upper = soup.getBounds().getSecond();

    assertThat(lower).isEqualTo(new Vector3D(-0.5, -0.5, -0.5));
    assertThat(upper).isEqualTo(new Vector3D(0.5, 0.5, 0.5));
  }
}
