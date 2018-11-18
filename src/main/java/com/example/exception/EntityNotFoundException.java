package com.example.exception;

public class EntityNotFoundException extends BaseException {

    private static final long serialVersionUID = 4065571577839079528L;

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
