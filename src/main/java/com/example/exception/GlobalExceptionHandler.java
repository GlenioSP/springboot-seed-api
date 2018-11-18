package com.example.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleException(EntityNotFoundException e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false));
        logger.error("A EntityNotFoundException was raised: ", e);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    private class ErrorDetails {
        private Date timestamp;
        private String message;
        private String details;

        private ErrorDetails(Date timestamp, String message, String details) {

            this.timestamp = timestamp;
            this.message = message;
            this.details = details;
        }
    }
}
