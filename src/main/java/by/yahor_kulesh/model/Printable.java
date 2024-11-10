package by.yahor_kulesh.model;

public interface Printable {
  default void print() {
    System.out.println(this);
  }
}
