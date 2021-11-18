package io.github.tn1.server.domain.chat.service;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	public void subscribeRoom(SocketIOClient client, String roomId) {
		client.joinRoom(roomId);
	}

}
