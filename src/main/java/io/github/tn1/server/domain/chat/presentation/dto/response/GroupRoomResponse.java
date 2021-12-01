package io.github.tn1.server.domain.chat.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupRoomResponse {

	@JsonUnwrapped
	private final RoomResponse response;

	private final int count;

}
