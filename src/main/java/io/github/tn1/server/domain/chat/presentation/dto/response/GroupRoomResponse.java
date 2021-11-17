package io.github.tn1.server.domain.chat.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupRoomResponse {

	private String roomId;
	private String roomName;
	private String lastMessage;
	private String photoUrl;
	private int count;

}
