package com.xalpol12.recipes.exception.custom;

public class RecipeNotIncludedException extends RuntimeException {
    public RecipeNotIncludedException() {
        super();
    }

    public RecipeNotIncludedException(String message) {
        super(message);
    }
}
