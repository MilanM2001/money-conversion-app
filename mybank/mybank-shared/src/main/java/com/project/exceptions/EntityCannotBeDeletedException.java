package com.project.exceptions;

public class EntityCannotBeDeletedException extends RuntimeException {
    public EntityCannotBeDeletedException(String message) {
        super(message);
    }
}
