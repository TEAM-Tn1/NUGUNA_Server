package io.github.tn1.server.global.utils.api.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class OtherServerExpiredTokenException extends ServerException {

	public OtherServerExpiredTokenException() {
		super(ErrorCode.OTHER_SERVER_EXPIRED_TOKEN);
	}

}
