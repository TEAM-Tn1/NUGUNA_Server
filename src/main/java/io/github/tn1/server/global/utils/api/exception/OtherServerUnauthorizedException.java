package io.github.tn1.server.global.utils.api.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class OtherServerUnauthorizedException extends ServerException {

	public OtherServerUnauthorizedException() {
		super(ErrorCode.OTHER_SERVER_UNAUTHORIZED);
	}

}
