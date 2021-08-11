package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class UserNotFoundException extends ServerException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

}
