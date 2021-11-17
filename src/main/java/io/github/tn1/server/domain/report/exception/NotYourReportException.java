package io.github.tn1.server.domain.report.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class NotYourReportException extends ServerException {

	public NotYourReportException() {
		super(ErrorCode.NOT_YOUR_REPORT);
	}

}
