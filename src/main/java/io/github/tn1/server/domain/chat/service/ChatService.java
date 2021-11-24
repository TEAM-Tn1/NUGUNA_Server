package io.github.tn1.server.domain.chat.service;

import java.util.List;
import java.util.stream.Collectors;

import io.github.tn1.server.domain.chat.domain.Member;
import io.github.tn1.server.domain.chat.domain.Message;
import io.github.tn1.server.domain.chat.domain.Room;
import io.github.tn1.server.domain.chat.domain.repository.MemberRepository;
import io.github.tn1.server.domain.chat.domain.repository.MessageRepository;
import io.github.tn1.server.domain.chat.domain.repository.RoomRepository;
import io.github.tn1.server.domain.chat.domain.types.MessageType;
import io.github.tn1.server.domain.chat.domain.types.RoomType;
import io.github.tn1.server.domain.chat.exception.NotYourRoomException;
import io.github.tn1.server.domain.chat.facade.RoomFacade;
import io.github.tn1.server.domain.chat.presentation.dto.request.ChatRequest;
import io.github.tn1.server.domain.chat.presentation.dto.response.CarrotRoomResponse;
import io.github.tn1.server.domain.chat.presentation.dto.response.GroupRoomResponse;
import io.github.tn1.server.domain.chat.presentation.dto.response.QueryMessageResponse;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final UserFacade userFacade;
	private final RoomRepository roomRepository;
	private final RoomFacade roomFacade;
	private final MessageRepository messageRepository;
	private final MemberRepository memberRepository;

	public List<CarrotRoomResponse> queryCarrotRoom() {
		return roomRepository.findByEmailAndType(userFacade.getCurrentEmail(),
				RoomType.CARROT)
				.stream().map(room -> {
							Message message = messageRepository.findTopByRoom(room)
									.orElse(null);
							return new CarrotRoomResponse(room.getId(),
									room.getHeadUser().getName(),
									message != null ? message.getContent() : null,
									room.getPhotoUrl());
						}).collect(Collectors.toList());
	}

	public List<GroupRoomResponse> queryGroupRoom() {
		return roomRepository.findByEmailAndType(userFacade.getCurrentEmail(),
				RoomType.GROUP)
				.stream().map(room -> {
					Message message = messageRepository.findTopByRoom(room)
							.orElse(null);
					return new GroupRoomResponse(room.getId(),
							room.getFeed().getTitle(),
							message != null ? message.getContent() : null,
							room.getPhotoUrl(),
							room.getFeed().getGroup()
									.getCurrentCount());
				}).collect(Collectors.toList());
	}

	public List<QueryMessageResponse> queryMessage(String roomId, int page) {
		Room room = roomFacade.getRoomById(roomId);
		User user = userFacade.getCurrentUser();

		if(memberRepository
				.findByUserAndRoom(user, room).isEmpty())
			throw new NotYourRoomException();

		return messageRepository.findByRoom(room, PageRequest.of(page, 10))
				.stream().map(message ->
						new QueryMessageResponse(message.getId(), message.getContent(),
								message.getType().name(), message.getMember().getUser().getEmail(),
								message.getMember().getUser().getName(),  message.getSentAt())
				).collect(Collectors.toList());
	}

	public Message saveMessage(ChatRequest chatRequest, User user) {
		Room room = roomFacade.getRoomById(chatRequest.getRoomId());
		Member member = memberRepository.findByUserAndRoom(user, room)
				.orElseThrow(NotYourRoomException::new);

		return messageRepository.save(
				Message.builder()
						.content(chatRequest.getMessage())
						.room(room)
						.member(member)
						.type(MessageType.SEND)
						.build()
		);
	}

}
