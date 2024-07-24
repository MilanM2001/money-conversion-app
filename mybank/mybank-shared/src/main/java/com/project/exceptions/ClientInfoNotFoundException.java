package com.project.exceptions;

public class ClientInfoNotFoundException extends RuntimeException {
    public ClientInfoNotFoundException(String message) {
        super(message);
    }
}
