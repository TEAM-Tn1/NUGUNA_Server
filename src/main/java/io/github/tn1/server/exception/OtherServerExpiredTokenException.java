package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class OtherServerExpiredTokenException extends ServerException {

	public OtherServerExpiredTokenException() {
		super(ErrorCode.OTHER_SERVER_EXPIRED_TOKEN);
	}

}
