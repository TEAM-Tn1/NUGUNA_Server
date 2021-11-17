package io.github.tn1.server.domain.user.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class ExpiredAccessTokenException extends ServerException {

    public ExpiredAccessTokenException() {
        super(ErrorCode.EXPIRED_ACCESS_TOKEN);
    }

}
