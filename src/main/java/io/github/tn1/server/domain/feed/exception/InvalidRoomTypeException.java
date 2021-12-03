package io.github.tn1.server.domain.feed.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class InvalidRoomTypeException extends ServerException {

	public InvalidRoomTypeException() {
		super(ErrorCode.INVALID_ROOM_TYPE);
	}

}
