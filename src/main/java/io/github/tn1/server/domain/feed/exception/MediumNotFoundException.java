package io.github.tn1.server.domain.feed.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class MediumNotFoundException extends ServerException {

	public MediumNotFoundException() {
		super(ErrorCode.MEDIUM_NOT_FOUND);
	}

}
