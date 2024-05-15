package com.xalpol12.recipes.exception;

import com.xalpol12.recipes.exception.custom.IncompleteUpdateFormException;
import com.xalpol12.recipes.exception.custom.RecipeNotIncludedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotImplementedException.class)
    public ResponseEntity<String> handleNotImplementedException(NotImplementedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IncompleteUpdateFormException.class)
    public ResponseEntity<String> handleIncompleteUpdateFormException(IncompleteUpdateFormException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RecipeNotIncludedException.class)
    public ResponseEntity<String> handleRecipeNotIncludedException(RecipeNotIncludedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OptimisticLockException.class)
    public ResponseEntity<String> handleOptimisticLockException(OptimisticLockException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    public ResponseEntity<String> handleMissingRequestHeader(MissingRequestHeaderException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_REQUIRED);
    }
}
