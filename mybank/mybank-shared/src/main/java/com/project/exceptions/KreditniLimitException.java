package com.project.exceptions;

public class KreditniLimitException extends RuntimeException {
    public KreditniLimitException(String message) {
        super(message);
    }
}
