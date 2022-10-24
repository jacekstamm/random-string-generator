package com.edrone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(TooMuchResultsWantedException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String tooMuchResultsWantedHandler(TooMuchResultsWantedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BadJobRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badJobRequestHandler(BadJobRequestException ex) {
        return ex.getMessage();
    }
}
