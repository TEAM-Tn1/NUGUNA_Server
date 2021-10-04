package io.github.tn1.server.exception;

import io.github.tn1.server.error.exception.ErrorCode;
import io.github.tn1.server.error.exception.ServerException;

public class QuestionResultNotFoundException extends ServerException {

	public QuestionResultNotFoundException() {
		super(ErrorCode.QUESTION_RESULT_NOT_FOUND);
	}

}
