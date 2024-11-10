package by.yahor_kulesh.utils;

public abstract class ObjectCollection {
  protected Object[] objects;

  protected int firstEmptyElement = 0;

  protected Object[] addAndResize(Object object, int capacity) {
    Object[] resizedArray = new Object[objects.length + capacity];
    fillResizedArray(resizedArray, object);
    return resizedArray;
  }

  protected void fillResizedArray(Object[] resizedArray, Object object) {
    int x = 0;
    while (x < firstEmptyElement) {
      resizedArray[x] = objects[x];
      x++;
    }
    resizedArray[x] = object;
  }

  public int size() {
    return firstEmptyElement;
  }
}
