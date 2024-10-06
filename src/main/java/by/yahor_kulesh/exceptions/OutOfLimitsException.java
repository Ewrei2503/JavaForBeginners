package by.yahor_kulesh.exceptions;

import java.util.Arrays;

public class OutOfLimitsException extends RuntimeException {
    public OutOfLimitsException(char[][] limits) {
        super("Out of limits! Must be between " + Arrays.deepToString(limits));
    }
}
