package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class AlreadyLikedFeedException extends ServerException {

	public AlreadyLikedFeedException() {
		super(ErrorCode.ALREADY_LIKED_FEED);
	}

}
