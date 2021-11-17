package io.github.tn1.server.domain.feed.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class AlreadyLikedFeedException extends ServerException {

	public AlreadyLikedFeedException() {
		super(ErrorCode.ALREADY_LIKED_FEED);
	}

}
