package io.github.tn1.server.dto.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionResultRequest {

	private Long questionId;
	private String reason;

}
