package io.github.tn1.server.domain.feed.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class FeedNotFoundException extends ServerException {

    public FeedNotFoundException() {
        super(ErrorCode.FEED_NOT_FOUND);
    }

}
