package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class ReportNotFoundException extends ServerException {

	public ReportNotFoundException() {
		super(ErrorCode.REPORT_NOT_FOUND);
	}

}
