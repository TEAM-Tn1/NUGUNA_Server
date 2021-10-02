package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class LikeNotFoundException extends ServerException {

	public LikeNotFoundException() {
		super(ErrorCode.LIKE_NOT_FOUND);
	}

}
