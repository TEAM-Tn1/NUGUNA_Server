package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class NotYourRoomException extends ServerException {

	public NotYourRoomException() {
		super(ErrorCode.NOT_YOUR_ROOM);
	}

}
