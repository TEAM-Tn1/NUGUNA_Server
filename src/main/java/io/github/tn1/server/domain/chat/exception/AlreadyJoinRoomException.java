package io.github.tn1.server.domain.chat.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class AlreadyJoinRoomException extends ServerException {

	public AlreadyJoinRoomException() {
		super(ErrorCode.ALREADY_JOIN_ROOM);
	}

}
