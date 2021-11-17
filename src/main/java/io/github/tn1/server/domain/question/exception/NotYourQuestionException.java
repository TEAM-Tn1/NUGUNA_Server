package io.github.tn1.server.domain.question.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class NotYourQuestionException extends ServerException {

	public NotYourQuestionException() {
		super(ErrorCode.NOT_YOUR_QUESTION);
	}

}
