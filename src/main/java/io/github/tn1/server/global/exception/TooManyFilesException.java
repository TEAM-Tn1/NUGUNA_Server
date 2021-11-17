package io.github.tn1.server.global.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;


public class TooManyFilesException extends ServerException {

    public TooManyFilesException() {
        super(ErrorCode.TOO_MANY_FILES);
    }

}
