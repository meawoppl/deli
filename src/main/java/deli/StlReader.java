package deli;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class StlReader {
  private final byte[] contents;

  public StlReader(File file) {
    try {
      contents = Files.toByteArray(file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public byte[] getHeader() {
    return Arrays.copyOfRange(contents, 0, 80);
  }

  public long getNTriangles() {
    ByteBuffer byteBuffer = ByteBuffer.wrap(contents);
    return byteBuffer.order(ByteOrder.nativeOrder()).getInt(80);
  }

  List<Triangle> getTriangles() {
    ByteBuffer byteBuffer = ByteBuffer.wrap(contents);
    byteBuffer.order(ByteOrder.nativeOrder());

    List<Triangle> triangles = new ArrayList<>();
    for (int t = 0; t < getNTriangles(); t++) {
      byteBuffer.position(80 + 4 + 12 + (50 * t));
      FloatBuffer fb = byteBuffer.asFloatBuffer();
      Vector3D[] points = new Vector3D[3];
      for (int point = 0; point < 3; point++) {
        double[] pt = new double[3];
        for (int idx = 0; idx < 3; idx++) {
          pt[idx] = fb.get();
        }
        points[point] = new Vector3D(pt);
      }
      triangles.add(new Triangle(points));
    }
    return triangles;
  }

  private static byte[] readContentIntoByteArray(File file) {
    FileInputStream fileInputStream = null;
    byte[] bFile = new byte[(int) file.length()];
    try {
      // convert file into array of bytes
      fileInputStream = new FileInputStream(file);
      fileInputStream.read(bFile);
      fileInputStream.close();
      for (int i = 0; i < bFile.length; i++) {
        System.out.print((char) bFile[i]);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bFile;
  }
}
