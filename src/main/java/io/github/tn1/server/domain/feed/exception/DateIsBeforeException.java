package io.github.tn1.server.domain.feed.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class DateIsBeforeException extends ServerException {

	public DateIsBeforeException() {
		super(ErrorCode.DATE_IS_BEFORE);
	}

}
