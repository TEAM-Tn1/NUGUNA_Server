package io.github.tn1.server.domain.chat.service;

import javax.transaction.Transactional;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import io.github.tn1.server.domain.chat.domain.Member;
import io.github.tn1.server.domain.chat.domain.Message;
import io.github.tn1.server.domain.chat.domain.Room;
import io.github.tn1.server.domain.chat.domain.repository.MemberRepository;
import io.github.tn1.server.domain.chat.domain.repository.RoomRepository;
import io.github.tn1.server.domain.chat.domain.types.MessageType;
import io.github.tn1.server.domain.chat.domain.types.RoomType;
import io.github.tn1.server.domain.chat.exception.AlreadyJoinRoomException;
import io.github.tn1.server.domain.chat.exception.RoomIsFullException;
import io.github.tn1.server.domain.chat.exception.RoomNotFoundException;
import io.github.tn1.server.domain.chat.facade.MessageFacade;
import io.github.tn1.server.domain.chat.presentation.dto.MessageDto;
import io.github.tn1.server.domain.feed.domain.Feed;
import io.github.tn1.server.domain.feed.facade.FeedFacade;
import io.github.tn1.server.domain.report.exception.ItsYourFeedException;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.domain.user.facade.UserFacade;
import io.github.tn1.server.global.socket.Name;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private final UserFacade userFacade;
	private final FeedFacade feedFacade;
	private final RoomRepository roomRepository;
	private final MemberRepository memberRepository;
	private final MessageFacade messageFacade;

	public void subscribeRoom(SocketIOClient client, String roomId) {
		client.joinRoom(roomId);
		client.sendEvent(Name.Subscribe.name(), "Subscribe Success");
	}

	@Transactional
	public void joinRoom(SocketIOClient client, SocketIOServer server, Long feedId) {
		User currentUser = userFacade.getCurrentUser(client);

		Feed feed = feedFacade.getFeedById(feedId);

		Room room;

		if(feed.isWriter(currentUser.getEmail()))
			throw new ItsYourFeedException();

		if(roomRepository.existsByFeedAndEmail(feed, currentUser.getEmail())){
			throw new AlreadyJoinRoomException();
		}

		if(feed.isUsedItem()) {
			room = roomRepository.save(
					Room.builder()
							.feed(feed)
							.user(feed.getUser())
							.type(RoomType.CARROT)
							.photoUrl(feedFacade.getFeedPhotoUrl(feed))
							.build());
			memberRepository.save(
					Member.builder()
							.user(feed.getUser())
							.room(room)
							.build()
			);
		} else {
			if(feed.getGroup().getCurrentCount() >=
					feed.getGroup().getHeadCount())
				throw new RoomIsFullException();
			feed.getGroup().increaseCurrentCount();
			room = roomRepository.findByFeed(feed)
					.orElseThrow(RoomNotFoundException::new);
		}

		memberRepository.save(
				Member.builder()
						.user(currentUser)
						.room(room)
						.build()
		);

		client.joinRoom(room.getId());

		Message message = messageFacade.saveMessage(currentUser, MessageType.JOIN, room,
				currentUser.getName() + " 님이 입장하셨습니다.");

		MessageDto messageDto = MessageDto.builder()
				.content(message.getContent())
				.email(currentUser.getEmail())
				.name(currentUser.getName())
				.sentAt(message.getSentAt().toString())
				.type(message.getType())
				.build();

		server.getRoomOperations(room.getId())
				.getClients()
				.forEach(member ->
						member.sendEvent(Name.Move.name(), messageDto)
				);
		client.sendEvent(Name.Room.name(), room.getId());
	}

}
