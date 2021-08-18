package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class FeedNotFoundException extends ServerException {

    public FeedNotFoundException() {
        super(ErrorCode.FEED_NOT_FOUND);
    }

}
