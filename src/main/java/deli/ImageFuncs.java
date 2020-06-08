package deli;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageFuncs {
  private ImageFuncs() {};

  public static void writeImage(BufferedImage image, String path) {
    try {
      // retrieve image
      File outputFile = new File(path);
      ImageIO.write(image, "png", outputFile);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Problem while writing image: %s", path));
    }
  }
}
