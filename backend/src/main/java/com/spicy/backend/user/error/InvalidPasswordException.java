package com.spicy.backend.user.error;

import com.spicy.backend.global.error.exception.BusinessException;

public class InvalidPasswordException extends BusinessException {

    public InvalidPasswordException() {
        super(UserErrorCode.INVALID_PASSWORD);
    }
}