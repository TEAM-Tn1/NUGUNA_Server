package io.github.tn1.server.global.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class FileUploadFailException extends ServerException {

    public FileUploadFailException() {
        super(ErrorCode.FILE_UPLOAD_FAIL);
    }

}
