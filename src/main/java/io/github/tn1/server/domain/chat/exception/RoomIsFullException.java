package io.github.tn1.server.domain.chat.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class RoomIsFullException extends ServerException {

	public RoomIsFullException() {
		super(ErrorCode.ROOM_IS_FULL);
	}

}
