package io.github.tn1.server.domain.notification.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class NotificationNotFoundException extends ServerException {

	public NotificationNotFoundException() {
		super(ErrorCode.NOTIFICATION_NOT_FOUND);
	}

}
