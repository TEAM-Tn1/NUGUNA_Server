package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class NotYourNotificationTagException extends ServerException {

	public NotYourNotificationTagException() {
		super(ErrorCode.NOT_YOUR_NOTIFICATION_TAG);
	}

}
