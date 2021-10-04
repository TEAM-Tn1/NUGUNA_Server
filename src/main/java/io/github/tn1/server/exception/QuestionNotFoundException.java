package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class QuestionNotFoundException extends ServerException {

	public QuestionNotFoundException() {
		super(ErrorCode.QUESTION_NOT_FOUND);
	}

}
