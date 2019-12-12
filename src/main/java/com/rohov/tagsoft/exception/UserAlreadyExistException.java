package com.rohov.tagsoft.exception;

public class UserAlreadyExistException extends BusinessException {
    public UserAlreadyExistException(String errorMsg) {
        super(errorMsg);
    }
}
