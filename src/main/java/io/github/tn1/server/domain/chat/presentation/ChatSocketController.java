package io.github.tn1.server.domain.chat.presentation;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import io.github.tn1.server.domain.chat.domain.Message;
import io.github.tn1.server.domain.chat.exception.InvalidNumberFormatException;
import io.github.tn1.server.domain.chat.presentation.dto.request.ChatRequest;
import io.github.tn1.server.domain.chat.service.ChatRoomService;
import io.github.tn1.server.domain.chat.service.ChatService;
import io.github.tn1.server.domain.chat.service.ChatSocketService;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.domain.user.exception.UserNotFoundException;
import io.github.tn1.server.domain.user.facade.UserFacade;
import io.github.tn1.server.global.socket.annotation.SocketController;
import io.github.tn1.server.global.socket.annotation.SocketMapping;
import lombok.RequiredArgsConstructor;

@SocketController
@RequiredArgsConstructor
public class ChatSocketController {

	private final ChatRoomService chatRoomService;
	private final ChatSocketService chatSocketService;
	private final UserFacade userFacade;
	private final ChatService chatService;

	@SocketMapping(endpoint = "subscribe", requestCls = String.class)
	public void subscribeRoom(SocketIOClient client, String roomId) {
		chatRoomService.subscribeRoom(client, roomId);
	}

	@SocketMapping(endpoint = "unsubscribe", requestCls = String.class)
	public void unsubscribeRoom(SocketIOClient client, String roomId) {
		chatRoomService.unsubscribeRoom(client, roomId);
	}

	@SocketMapping(endpoint = "join", requestCls = String.class)
	public void joinRoom(SocketIOClient client, SocketIOServer server, String feedId) {
		try {
			chatRoomService.joinRoom(client, server, Long.parseLong(feedId));
		} catch (NumberFormatException e) {
			throw new InvalidNumberFormatException();
		}
	}

	@SocketMapping(endpoint = "leave", requestCls = String.class)
	public void leaveRoom(SocketIOClient client, SocketIOServer server, String roomId) {
		chatRoomService.leaveRoom(client, server, roomId);
	}

	@SocketMapping(endpoint = "message", requestCls = ChatRequest.class)
	public void sendMessage(SocketIOClient client, SocketIOServer server, ChatRequest request) {
		if(request.getRoomId() == null)
			throw new UserNotFoundException();
		User user = userFacade.getCurrentUser(client);
		Message message = chatService.saveMessage(request, user);
		chatSocketService.sendChatMessage(message, user, request.getRoomId(), server);
	}

}
