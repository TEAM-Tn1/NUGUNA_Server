package io.github.tn1.server.domain.report.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class ReportResultNotFoundException extends ServerException {

	public ReportResultNotFoundException() {
		super(ErrorCode.REPORT_RESULT_NOT_FOUND);
	}

}
