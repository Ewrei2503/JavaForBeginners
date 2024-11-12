package by.yahor_kulesh.utils;

import java.util.Arrays;

public abstract class ObjectCollection {
  protected Object[] objects;

  protected int firstEmptyElement = 0;

  protected Object[] addAndResize(Object object, int capacity) {
    Object[] resizedArray = new Object[objects.length + capacity];
    resizedArray = fillResizedArray(resizedArray, object);
    return resizedArray;
  }

  protected Object[] fillResizedArray(Object[] resizedArray, Object object) {
    resizedArray = Arrays.copyOf(objects, resizedArray.length);
    resizedArray[firstEmptyElement] = object;
    return resizedArray;
  }

  public int size() {
    return firstEmptyElement;
  }
}
