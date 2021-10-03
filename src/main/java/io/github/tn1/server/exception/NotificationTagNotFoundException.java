package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class NotificationTagNotFoundException extends ServerException {

	public NotificationTagNotFoundException() {
		super(ErrorCode.NOTIFICATION_TAG_NOT_FOUND);
	}

}
