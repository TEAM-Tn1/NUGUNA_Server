package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class TooManyTagsException extends ServerException {

    public TooManyTagsException() {
        super(ErrorCode.TOO_MANY_TAGS);
    }

}
