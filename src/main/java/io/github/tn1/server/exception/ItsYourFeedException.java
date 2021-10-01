package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class ItsYourFeedException extends ServerException {

	public ItsYourFeedException() {
		super(ErrorCode.ITS_YOUR_FEED);
	}

}
