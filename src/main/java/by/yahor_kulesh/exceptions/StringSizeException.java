package by.yahor_kulesh.exceptions;

public class StringSizeException extends RuntimeException {
  public StringSizeException() {
    super("Wrong string size. Try again!");
  }
}
