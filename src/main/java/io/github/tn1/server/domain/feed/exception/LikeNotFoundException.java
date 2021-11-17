package io.github.tn1.server.domain.feed.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class LikeNotFoundException extends ServerException {

	public LikeNotFoundException() {
		super(ErrorCode.LIKE_NOT_FOUND);
	}

}
