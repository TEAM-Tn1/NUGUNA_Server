package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class NotUserReportException extends ServerException {

	public NotUserReportException() {
		super(ErrorCode.NOT_USER_REPORT);
	}

}
