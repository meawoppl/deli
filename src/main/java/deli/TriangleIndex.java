package deli;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class TriangleIndex {
  private final ImmutableList<Triangle> indexed;
  private final double[] index;
  private final Function<Triangle, Double> orderer;

  public static final ImmutableList<Triangle> EMPTY = ImmutableList.copyOf(new Triangle[]{});

  public TriangleIndex(Collection<Triangle> tris, Function<Triangle, Double> orderer) {
    List<Triangle> temp = Lists.newArrayList(tris);
    temp.sort(Comparator.comparing(orderer));
    indexed = ImmutableList.copyOf(temp);
    index = indexed.stream().mapToDouble(orderer::apply).toArray();
    this.orderer = orderer;
  }

  public double[] getIndex() {
    return Arrays.copyOf(index, index.length);
  }

  public ImmutableList<Triangle> getTriangles() {
    return indexed;
  }

  private int indexToArrayBound(int index) {
    if (index < 0){
      if (index == -1){
        return 0;
      }
      return -index-1;
    }
    return index;
  }

  private double ulpInc(double value){
    return value + Math.ulp(value);
  }

  private double ulpDec(double value) {
    return value - Math.ulp(value);
  }

  private int searchIndex(double value){
    return indexToArrayBound(Arrays.binarySearch(index, value));
  }

  public ImmutableList<Triangle> between(Triangle min, Triangle max){
    return  between(orderer.apply(min), orderer.apply(max));
  }

  public ImmutableList<Triangle> between(double minInclusive, double maxInclusive){
    int lowerIndex = searchIndex(minInclusive);
    int upperIndex = searchIndex(ulpInc(maxInclusive));

    return indexed.subList(lowerIndex, upperIndex);
  }

  public ImmutableList<Triangle> lte(double lt) {
    if (index.length == 0){
      return EMPTY;
    }

    double lowestIndexValue = index[0];
    if (lt < lowestIndexValue) {
      return EMPTY;
    }

    int upperIndex = searchIndex(lt);
    return indexed.subList(0, upperIndex);

  }

  public ImmutableList<Triangle> lt(Triangle lt) {
    return lt(orderer.apply(lt));
  }

  public ImmutableList<Triangle> lt(double lt) {
    return lte(ulpDec(lt));
  }

  public ImmutableList<Triangle> gte(double gte) {
    if (index.length == 0){
      return EMPTY;
    }

    double highestIndexValue = index[index.length - 1];
    if (gte > highestIndexValue) {
      return EMPTY;
    }

    int lowerIndex = searchIndex(gte);
    return indexed.subList(lowerIndex, index.length);
  }
  public ImmutableList<Triangle> gt(Triangle gt) {
    return gt(orderer.apply(gt));
  }

  public ImmutableList<Triangle> gt(double gt) {
    return gte(ulpInc(gt));
  }
}
