package io.github.tn1.server.domain.notification.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class NotificationTagNotFoundException extends ServerException {

	public NotificationTagNotFoundException() {
		super(ErrorCode.NOTIFICATION_TAG_NOT_FOUND);
	}

}
