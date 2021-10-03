package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class NotYourReportException extends ServerException {

	public NotYourReportException() {
		super(ErrorCode.NOT_YOUR_REPORT);
	}

}
