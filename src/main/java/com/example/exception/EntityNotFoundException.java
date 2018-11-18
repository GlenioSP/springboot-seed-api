package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntityNotFoundException extends BaseException {

    private static final long serialVersionUID = 4065571577839079528L;

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
