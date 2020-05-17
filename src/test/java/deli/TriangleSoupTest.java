package deli;

import org.assertj.core.api.Assertions;
import org.junit.Test;


public class TriangleSoupTest extends Assertions {

  @Test
  public void testStats() {
    StlReader reader = new StlReader(new StlReaderTest().loadTestCube());
    TriangleSoup soup = new TriangleSoup(reader.getTriangles());
    System.out.println(soup.getBounds());
  }

}