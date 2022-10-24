package com.edrone.exception;

public class TooMuchResultsWantedException extends RuntimeException {
    public TooMuchResultsWantedException(String chars, int maxLength, int wantedResults) {
        super(String.format("Number of possible strings cannot exceed the requested size: (length of '%s') ^ %d < %d",
          chars, maxLength, wantedResults));
    }
}
