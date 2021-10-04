package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class NotificationNotFoundException extends ServerException {

	public NotificationNotFoundException() {
		super(ErrorCode.NOTIFICATION_NOT_FOUND);
	}

}
