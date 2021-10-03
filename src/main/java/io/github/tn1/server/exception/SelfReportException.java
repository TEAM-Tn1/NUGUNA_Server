package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class SelfReportException extends ServerException {

	public SelfReportException() {
		super(ErrorCode.SELF_REPORT);
	}

}
