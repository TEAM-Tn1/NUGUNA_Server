package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class AlreadyJoinRoomException extends ServerException {

	public AlreadyJoinRoomException() {
		super(ErrorCode.ALREADY_JOIN_ROOM);
	}

}
