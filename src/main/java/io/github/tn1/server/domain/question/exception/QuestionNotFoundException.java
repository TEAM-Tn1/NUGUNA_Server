package io.github.tn1.server.domain.question.exception;

import io.github.tn1.server.global.error.exception.ErrorCode;
import io.github.tn1.server.global.error.exception.ServerException;

public class QuestionNotFoundException extends ServerException {

	public QuestionNotFoundException() {
		super(ErrorCode.QUESTION_NOT_FOUND);
	}

}
