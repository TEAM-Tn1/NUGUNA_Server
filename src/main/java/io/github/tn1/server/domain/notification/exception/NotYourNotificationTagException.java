package io.github.tn1.server.domain.notification.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class NotYourNotificationTagException extends ServerException {

	public NotYourNotificationTagException() {
		super(ErrorCode.NOT_YOUR_NOTIFICATION_TAG);
	}

}
