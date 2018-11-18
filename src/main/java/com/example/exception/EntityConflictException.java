package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntityConflictException extends BaseException {

    private static final long serialVersionUID = -255284434459554295L;

    public EntityConflictException(String msg) {
        super(msg);
    }
}
