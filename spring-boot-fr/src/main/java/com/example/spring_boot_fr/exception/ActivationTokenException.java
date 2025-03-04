package com.example.spring_boot_fr.exception;

/**
 * @author Dave AKN
 * @version 1.0
 */
public class ActivationTokenException extends RuntimeException {
    public ActivationTokenException(String message) {
        super(message);
    }
}
