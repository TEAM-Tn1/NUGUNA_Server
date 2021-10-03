package io.github.tn1.server.dto.chat.response;

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
