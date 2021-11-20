package io.github.tn1.server.domain.chat.presentation.dto;

import io.github.tn1.server.domain.chat.domain.types.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MessageDto {

	private final String content;

	private final String sentAt;

	private final MessageType type;

	private final String name;

	private final String email;

}
