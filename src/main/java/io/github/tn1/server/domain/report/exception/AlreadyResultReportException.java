package io.github.tn1.server.domain.report.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class AlreadyResultReportException extends ServerException {

	public AlreadyResultReportException() {
		super(ErrorCode.ALREADY_RESULT_REPORT);
	}

}
