package com.xalpol12.recipes.exception.custom;

public class IncompleteUpdateFormException extends RuntimeException {
    public IncompleteUpdateFormException() {
        super();
    }

    public IncompleteUpdateFormException(String message) {
        super(message);
    }
}
