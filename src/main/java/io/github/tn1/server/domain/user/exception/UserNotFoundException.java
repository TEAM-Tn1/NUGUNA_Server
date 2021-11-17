package io.github.tn1.server.domain.user.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class UserNotFoundException extends ServerException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

}
