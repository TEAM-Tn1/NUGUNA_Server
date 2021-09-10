package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class MediumNotFoundException extends ServerException {

	public MediumNotFoundException() {
		super(ErrorCode.MEDIUM_NOT_FOUND);
	}

}
