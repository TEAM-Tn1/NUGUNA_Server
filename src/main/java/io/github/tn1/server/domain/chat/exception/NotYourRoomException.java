package io.github.tn1.server.domain.chat.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class NotYourRoomException extends ServerException {

	public NotYourRoomException() {
		super(ErrorCode.NOT_YOUR_ROOM);
	}

}
