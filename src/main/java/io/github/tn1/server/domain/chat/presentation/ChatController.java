package io.github.tn1.server.domain.chat.presentation;

import java.util.List;

import io.github.tn1.server.domain.chat.presentation.dto.response.CarrotRoomResponse;
import io.github.tn1.server.domain.chat.presentation.dto.response.GroupRoomResponse;
import io.github.tn1.server.domain.chat.presentation.dto.response.QueryMessageResponse;
import io.github.tn1.server.domain.chat.presentation.dto.response.RoomInformationResponse;
import io.github.tn1.server.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;

	@GetMapping("/carrot")
	public List<CarrotRoomResponse> queryCarrotRoom() {
		return chatService.queryCarrotRoom();
	}

	@GetMapping("/group")
	public List<GroupRoomResponse> queryGroupRoom() {
		return chatService.queryGroupRoom();
	}

	@GetMapping("/content")
	public List<QueryMessageResponse> queryMessage(@RequestParam("page") int page, @RequestParam("room_id") String roomId) {
		return chatService.queryMessage(roomId, page);
	}
	
	@GetMapping("/information")
	public RoomInformationResponse queryInformation(@RequestParam("room_id") String roomId) {
		return chatService.queryInformation(roomId);
	}

}
