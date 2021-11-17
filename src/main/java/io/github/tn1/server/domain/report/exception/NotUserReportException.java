package io.github.tn1.server.domain.report.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class NotUserReportException extends ServerException {

	public NotUserReportException() {
		super(ErrorCode.NOT_USER_REPORT);
	}

}
