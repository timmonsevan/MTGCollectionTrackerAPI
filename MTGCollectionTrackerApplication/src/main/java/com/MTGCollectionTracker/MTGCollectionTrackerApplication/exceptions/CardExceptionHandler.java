package com.MTGCollectionTracker.MTGCollectionTrackerApplication.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * exception handler converts thrown exceptions into JSON objects via CardErrorResponse pojo
 * @author timmonsevan
 */
@ControllerAdvice
public class CardExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CardErrorResponse> handleEx (CardNotFoundException ex) {

        CardErrorResponse error = new CardErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CardErrorResponse> handleEx (Exception ex) {

        CardErrorResponse error = new CardErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
