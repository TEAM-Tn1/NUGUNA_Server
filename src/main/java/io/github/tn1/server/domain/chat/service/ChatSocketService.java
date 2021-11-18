package io.github.tn1.server.domain.chat.service;

import com.corundumstudio.socketio.SocketIOServer;
import io.github.tn1.server.domain.chat.domain.Message;
import io.github.tn1.server.domain.chat.domain.types.MessageType;
import io.github.tn1.server.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatSocketService {

	private final UserFacade userFacade;

	public void sendChatMessage(Message message, String roomId, SocketIOServer server) {
		server.getRoomOperations(roomId)
				.getClients()
				.forEach(client ->
					client.sendEvent(MessageType.SEND.name(), message)
				);
	}

}
