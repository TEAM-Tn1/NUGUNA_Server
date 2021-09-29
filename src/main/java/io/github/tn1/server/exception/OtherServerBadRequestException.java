package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class OtherServerBadRequestException extends ServerException {

	public OtherServerBadRequestException() {
		super(ErrorCode.OTHER_SERVER_BAD_REQUEST);
	}

}
