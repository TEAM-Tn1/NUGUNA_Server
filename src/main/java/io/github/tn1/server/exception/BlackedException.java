package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class BlackedException extends ServerException {

    public BlackedException() {
        super(ErrorCode.BLACKED);
    }

}
