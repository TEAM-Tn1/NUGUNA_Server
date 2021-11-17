package io.github.tn1.server.domain.report.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class ReportNotFoundException extends ServerException {

	public ReportNotFoundException() {
		super(ErrorCode.REPORT_NOT_FOUND);
	}

}
