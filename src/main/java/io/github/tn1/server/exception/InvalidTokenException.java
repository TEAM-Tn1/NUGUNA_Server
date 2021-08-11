package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class InvalidTokenException extends ServerException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }

}
