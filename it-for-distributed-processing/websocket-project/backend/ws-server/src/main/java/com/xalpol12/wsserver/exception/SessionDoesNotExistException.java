package com.xalpol12.wsserver.exception;

public class SessionDoesNotExistException extends RuntimeException {
    public SessionDoesNotExistException(String message) {
        super(message);
    }
}
