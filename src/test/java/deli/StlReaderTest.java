package deli;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class StlReaderTest extends Assertions {

  public File loadTestCube() {

    return new File(getClass().getClassLoader().getResource("stls/cube.stl").getFile());
  }

  @Test
  public void getHeader() {
    StlReader reader = new StlReader(loadTestCube());
    String header = new String(reader.getHeader());
    assertThat(header).startsWith("binary stl file");
    assertThat(header.length()).isEqualTo(80);
  }

  @Test
  public void getNTriangles() {
    StlReader reader = new StlReader(loadTestCube());
    assertThat(reader.getNTriangles()).isEqualTo(12);
  }

  @Test
  public void getTriangles() {
    StlReader reader = new StlReader(loadTestCube());
    List<Triangle> tris = reader.getTriangles();

    assertThat(tris.size()).isEqualTo(12);
    tris.forEach(System.out::println);
  }
}
