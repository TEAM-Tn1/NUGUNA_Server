package io.github.tn1.server.domain.question.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class AlreadyResultQuestionException extends ServerException {

	public AlreadyResultQuestionException() {
		super(ErrorCode.ALREADY_RESULT_QUESTION);
	}

}
