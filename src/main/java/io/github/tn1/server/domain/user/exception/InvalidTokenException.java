package io.github.tn1.server.domain.user.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class InvalidTokenException extends ServerException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }

}
