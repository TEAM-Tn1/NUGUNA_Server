package io.github.tn1.server.domain.notification.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class AlreadyRegisteredTagException extends ServerException {

	public AlreadyRegisteredTagException() {
		super(ErrorCode.ALREADY_REGISTERED_TAG);
	}

}
