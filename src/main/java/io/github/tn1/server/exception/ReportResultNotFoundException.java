package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class ReportResultNotFoundException extends ServerException {

	public ReportResultNotFoundException() {
		super(ErrorCode.REPORT_RESULT_NOT_FOUND);
	}

}
