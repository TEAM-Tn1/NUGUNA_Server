package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class AlreadyResultReportException extends ServerException {

	public AlreadyResultReportException() {
		super(ErrorCode.ALREADY_RESULT_REPORT);
	}

}
