package io.github.tn1.server.domain.report.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class AlreadyReportedUserException extends ServerException {

	public AlreadyReportedUserException() {
		super(ErrorCode.ALREADY_REPORTED_USER);
	}

}
