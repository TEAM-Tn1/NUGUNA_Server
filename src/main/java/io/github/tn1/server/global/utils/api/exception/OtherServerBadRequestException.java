package io.github.tn1.server.global.utils.api.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class OtherServerBadRequestException extends ServerException {

	public OtherServerBadRequestException() {
		super(ErrorCode.OTHER_SERVER_BAD_REQUEST);
	}

}
