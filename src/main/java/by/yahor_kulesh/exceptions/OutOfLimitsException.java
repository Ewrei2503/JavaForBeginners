package by.yahor_kulesh.exceptions;

import java.util.Arrays;

public class OutOfLimitsException extends RuntimeException {
  public OutOfLimitsException(char[][] limits, String variable) {
    super(variable + " is out of limits! Must be between " + Arrays.deepToString(limits));
  }
}
