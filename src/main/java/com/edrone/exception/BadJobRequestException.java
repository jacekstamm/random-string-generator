package com.edrone.exception;

public class BadJobRequestException extends RuntimeException {
    public BadJobRequestException(int resultsNumber, int minLength, int maxLength, String stringOfChars) {
        super(String.format("Illegal request { Wanted Results: %s, Min Length: %d, Max Length: %d, Given string of chars: %s }", resultsNumber, minLength, maxLength, stringOfChars));
    }
}
