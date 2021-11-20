package io.github.tn1.server.domain.chat.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class InvalidNumberFormatException extends ServerException {

	public InvalidNumberFormatException() {
		super(ErrorCode.INVALID_NUMBER_FORMAT);
	}

}
