package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class OtherServerUnauthorizedException extends ServerException {

	public OtherServerUnauthorizedException() {
		super(ErrorCode.OTHER_SERVER_UNAUTHORIZED);
	}

}
