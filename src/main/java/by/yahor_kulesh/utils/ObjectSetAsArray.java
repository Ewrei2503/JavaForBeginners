package by.yahor_kulesh.utils;

import java.util.Objects;
import java.util.function.Consumer;

public class ObjectSetAsArray extends ObjectCollection {

  private static final double MAX_CAPACITY_PERCENTAGE = 0.75;

  public ObjectSetAsArray() {
    objects = new Object[16];
  }

  public boolean add(Object o) {
    if (contains(o)) {
      return false;
    } else if (firstEmptyElement >= objects.length * MAX_CAPACITY_PERCENTAGE) {
      objects = addAndResize(o, objects.length);
      firstEmptyElement++;
    } else {
      objects[firstEmptyElement] = o;
      firstEmptyElement++;
    }
    return true;
  }

  public boolean contains(Object o) {
    if (firstEmptyElement > 0) {
      for (Object object : objects) {
        if (!Objects.isNull(object) && object.equals(o)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean remove(Object o) {
    for (int i = 0; i < firstEmptyElement; i++) {
      if (objects[i].equals(o)) {
        if (firstEmptyElement - 1 == i) {
          objects[i] = null;
        } else {
          int j = i;
          while (j < firstEmptyElement) {
            objects[j] = objects[j + 1];
            j += 1;
          }
        }
        firstEmptyElement--;
        return false;
      }
    }
    return true;
  }

  public void iterate(Consumer<? super Object> action) {
    for (Object object : objects) {
      action.accept(object);
    }
  }
}
