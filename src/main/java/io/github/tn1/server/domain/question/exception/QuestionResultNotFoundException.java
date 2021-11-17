package io.github.tn1.server.domain.question.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class QuestionResultNotFoundException extends ServerException {

	public QuestionResultNotFoundException() {
		super(ErrorCode.QUESTION_RESULT_NOT_FOUND);
	}

}
