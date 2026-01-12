package com.spicy.backend.user.error;

import com.spicy.backend.global.error.exception.BusinessException;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException() {
        super(UserErrorCode.INVALID_TOKEN);
    }
}
