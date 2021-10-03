package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class AlreadyRegisteredTagException extends ServerException {

	public AlreadyRegisteredTagException() {
		super(ErrorCode.ALREADY_REGISTERED_TAG);
	}

}
