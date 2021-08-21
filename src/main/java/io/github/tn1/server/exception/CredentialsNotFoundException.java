package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class CredentialsNotFoundException extends ServerException {

    public CredentialsNotFoundException() {
        super(ErrorCode.CREDENTIALS_NOT_FOUND);
    }

}
