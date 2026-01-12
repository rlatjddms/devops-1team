package com.spicy.backend.user.error;

import com.spicy.backend.global.error.exception.BusinessException;

public class DuplicateLoginIdException extends BusinessException {

    public DuplicateLoginIdException() {
        super(UserErrorCode.DUPLICATE_LOGIN_ID);
    }
}
