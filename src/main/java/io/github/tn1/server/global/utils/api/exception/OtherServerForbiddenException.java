package io.github.tn1.server.global.utils.api.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class OtherServerForbiddenException extends ServerException {

	public OtherServerForbiddenException() {
		super(ErrorCode.OTHER_SERVER_FORBIDDEN);
	}

}
