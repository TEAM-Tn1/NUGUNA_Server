package io.github.tn1.server.domain.chat.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class RoomNotFoundException extends ServerException {

	public RoomNotFoundException() {
		super(ErrorCode.ROOM_NOT_FOUND);
	}

}
