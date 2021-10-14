package io.github.tn1.server.dto.chat.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryMessageResponse {

	private final String message;
	private final String type;
	private final String email;
	private final String name;
	private final LocalDateTime sentAt;

}
