package io.github.tn1.server.domain.notification.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class NotYourNotificationException extends ServerException {

	public NotYourNotificationException() {
		super(ErrorCode.NOT_YOUR_NOTIFICATION);
	}

}
