package io.github.tn1.server.domain.report.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class NotFeedReportException extends ServerException {

	public NotFeedReportException() {
		super(ErrorCode.NOT_FEED_REPORT);
	}

}
