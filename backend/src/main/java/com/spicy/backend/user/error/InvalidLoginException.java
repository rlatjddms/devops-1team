package com.spicy.backend.user.error;

import com.spicy.backend.global.error.exception.BusinessException;

public class InvalidLoginException extends BusinessException {

    public InvalidLoginException() {
        super(UserErrorCode.INVALID_LOGIN);
    }
}
