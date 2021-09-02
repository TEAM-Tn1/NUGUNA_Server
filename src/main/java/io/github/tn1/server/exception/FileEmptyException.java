package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class FileEmptyException extends ServerException {

    public FileEmptyException() {
        super(ErrorCode.FILE_EMPTY);
    }

}
