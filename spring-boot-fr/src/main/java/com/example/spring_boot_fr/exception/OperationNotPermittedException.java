package com.example.spring_boot_fr.exception;

/**
 * @author Dave AKN
 * @version 1.0
 */
public class OperationNotPermittedException extends RuntimeException {
    public OperationNotPermittedException() {
    }

    public OperationNotPermittedException(String message) {
        super(message);
    }
}
