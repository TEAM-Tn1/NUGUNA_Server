package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class OtherServerForbiddenException extends ServerException {

	public OtherServerForbiddenException() {
		super(ErrorCode.OTHER_SERVER_FORBIDDEN);
	}

}
