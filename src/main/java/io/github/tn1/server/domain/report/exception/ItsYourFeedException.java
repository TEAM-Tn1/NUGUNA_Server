package io.github.tn1.server.domain.report.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class ItsYourFeedException extends ServerException {

	public ItsYourFeedException() {
		super(ErrorCode.ITS_YOUR_FEED);
	}

}
