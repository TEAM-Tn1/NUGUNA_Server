package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;


public class TooManyFilesException extends ServerException {

    public TooManyFilesException() {
        super(ErrorCode.TOO_MANY_FILES);
    }

}
