package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class AlreadyResultQuestionException extends ServerException {

	public AlreadyResultQuestionException() {
		super(ErrorCode.ALREADY_RESULT_QUESTION);
	}

}
