package com.example.exception;

class BaseException extends RuntimeException {

    private static final long serialVersionUID = -475305829581800450L;

    BaseException() { }

    BaseException(String msg) {
        super(msg);
    }

    BaseException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    BaseException(Throwable throwable) {
        super(throwable);
    }

    BaseException(String msg, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(msg, throwable, enableSuppression, writableStackTrace);
    }
}
