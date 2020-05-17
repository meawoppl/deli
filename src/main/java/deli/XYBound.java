package deli;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class XYBound {
  private Vector2D min;
  private Vector2D max;

  public XYBound(Vector2D min, Vector2D max) {
    this.min = min;
    this.max = max;
  }

  public Vector2D getMin() {
    return min;
  }

  public Vector2D getMax() {
    return max;
  }
}
