package by.yahor_kulesh.utils;

import java.util.Arrays;

public class ObjectArray extends ObjectCollection {

  public ObjectArray() {
    objects = new Object[10];
  }

  @Override
  public String toString() {
    return "ObjectArray{"
        + "objects="
        + Arrays.toString(Arrays.copyOf(objects, firstEmptyElement))
        + "} ";
  }

  public void add(Object object) {
    if (firstEmptyElement < objects.length) {
      objects[firstEmptyElement] = object;
      firstEmptyElement++;
    } else {
      objects = addAndResize(object, objects.length >> 1);
    }
  }

  public Object getByIndex(int index) {
    if (index < 0 || index >= firstEmptyElement) {
      throw new IndexOutOfBoundsException(
          "Index "
              + index
              + " is out of bounds. TicketRepo's last index is "
              + (firstEmptyElement - 1));
    }
    return objects[index];
  }

  public void removeByIndex(int index) {
    if (index >= firstEmptyElement || index < 0) {
      throw new IndexOutOfBoundsException(
          "Index "
              + index
              + " is out of bounds. TicketRepo's last index is "
              + (firstEmptyElement - 1));
    } else {
      while (index < objects.length - 2) {
        objects[index] = objects[index + 1];
        index++;
      }
      firstEmptyElement--;
    }
  }
}
