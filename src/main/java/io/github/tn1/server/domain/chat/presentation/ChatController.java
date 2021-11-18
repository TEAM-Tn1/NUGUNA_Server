package io.github.tn1.server.domain.chat.presentation;

import java.util.List;

import javax.validation.Valid;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import io.github.tn1.server.domain.chat.domain.Message;
import io.github.tn1.server.domain.chat.presentation.dto.request.ChatRequest;
import io.github.tn1.server.domain.chat.presentation.dto.request.JoinRequest;
import io.github.tn1.server.domain.chat.presentation.dto.request.LeaveRequest;
import io.github.tn1.server.domain.chat.presentation.dto.request.QueryMessageRequest;
import io.github.tn1.server.domain.chat.presentation.dto.response.CarrotRoomResponse;
import io.github.tn1.server.domain.chat.presentation.dto.response.GroupRoomResponse;
import io.github.tn1.server.domain.chat.presentation.dto.response.JoinResponse;
import io.github.tn1.server.domain.chat.presentation.dto.response.QueryMessageResponse;
import io.github.tn1.server.domain.chat.service.ChatService;
import io.github.tn1.server.domain.chat.service.ChatSocketService;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.domain.user.facade.UserFacade;
import io.github.tn1.server.global.socket.annotation.SocketController;
import io.github.tn1.server.global.socket.annotation.SocketMapping;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@SocketController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;
	private final ChatSocketService chatSocketService;
	private final UserFacade userFacade;

	@PostMapping("/room")
	public JoinResponse joinRoom(@RequestBody @Valid JoinRequest request) {
		return chatService.joinRoom(request.getFeedId());
	}

	@DeleteMapping("/room")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void leaveRoom(@RequestBody @Valid LeaveRequest request) {
		chatService.leaveRoom(request.getRoomId());
	}

	@GetMapping("/carrot")
	public List<CarrotRoomResponse> queryCarrotRoom() {
		return chatService.queryCarrotRoom();
	}

	@GetMapping("/group")
	public List<GroupRoomResponse> queryGroupRoom() {
		return chatService.queryGroupRoom();
	}

	@GetMapping("/content")
	public List<QueryMessageResponse> queryMessage(@RequestBody @Valid QueryMessageRequest request, @RequestParam("page") int page) {
		return chatService.queryMessage(request, page);
	}

	@SocketMapping(endpoint = "message", requestCls = ChatRequest.class)
	public void sendMessage(SocketIOClient client, SocketIOServer server, ChatRequest request) {
		User user = userFacade.getCurrentUser(client);
		Message message = chatService.saveMessage(request, user);
		chatSocketService.sendChatMessage(message, request.getRoomId(), server);
	}

}
