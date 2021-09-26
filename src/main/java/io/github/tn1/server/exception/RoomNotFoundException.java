package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class RoomNotFoundException extends ServerException {

	public RoomNotFoundException() {
		super(ErrorCode.ROOM_NOT_FOUND);
	}

}
