package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends BaseException {

    private static final long serialVersionUID = 6150554475676803638L;

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
