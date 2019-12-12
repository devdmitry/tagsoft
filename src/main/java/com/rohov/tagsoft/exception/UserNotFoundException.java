package com.rohov.tagsoft.exception;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
