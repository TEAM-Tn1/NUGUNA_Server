package io.github.tn1.server.domain.feed.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class TooManyTagsException extends ServerException {

    public TooManyTagsException() {
        super(ErrorCode.TOO_MANY_TAGS);
    }

}
