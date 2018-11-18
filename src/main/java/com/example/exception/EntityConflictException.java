package com.example.exception;

public class EntityConflictException extends BaseException {

    private static final long serialVersionUID = -255284434459554295L;

    public EntityConflictException(String msg) {
        super(msg);
    }
}
