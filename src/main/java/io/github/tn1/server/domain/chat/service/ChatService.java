package io.github.tn1.server.domain.chat.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.github.tn1.server.domain.chat.presentation.dto.request.QueryMessageRequest;
import io.github.tn1.server.domain.chat.presentation.dto.response.CarrotRoomResponse;
import io.github.tn1.server.domain.chat.presentation.dto.response.GroupRoomResponse;
import io.github.tn1.server.domain.chat.presentation.dto.response.JoinResponse;
import io.github.tn1.server.domain.chat.presentation.dto.response.QueryMessageResponse;
import io.github.tn1.server.domain.chat.domain.Member;
import io.github.tn1.server.domain.chat.domain.repository.MemberRepository;
import io.github.tn1.server.domain.chat.domain.repository.MessageRepository;
import io.github.tn1.server.domain.chat.domain.Room;
import io.github.tn1.server.domain.chat.domain.repository.RoomRepository;
import io.github.tn1.server.domain.chat.domain.types.RoomType;
import io.github.tn1.server.domain.feed.domain.Feed;
import io.github.tn1.server.domain.feed.domain.repository.FeedRepository;
import io.github.tn1.server.domain.feed.domain.FeedMedium;
import io.github.tn1.server.domain.feed.domain.repository.FeedMediumRepository;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.domain.user.domain.repository.UserRepository;
import io.github.tn1.server.domain.chat.exception.AlreadyJoinRoomException;
import io.github.tn1.server.domain.report.exception.ItsYourFeedException;
import io.github.tn1.server.domain.chat.exception.NotYourRoomException;
import io.github.tn1.server.domain.chat.exception.RoomIsFullException;
import io.github.tn1.server.domain.chat.exception.RoomNotFoundException;
import io.github.tn1.server.domain.feed.facade.FeedFacade;
import io.github.tn1.server.domain.user.facade.UserFacade;
import io.github.tn1.server.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final UserFacade userFacade;
	private final FeedFacade feedFacade;
	private final UserRepository userRepository;
	private final FeedRepository feedRepository;
	private final FeedMediumRepository feedMediumRepository;
	private final RoomRepository roomRepository;
	private final MessageRepository messageRepository;
	private final MemberRepository memberRepository;

	private final S3Util s3Util;

	@Transactional
	public JoinResponse joinRoom(Long feedId) {
		User currentUser = userFacade.getCurrentUser();

		Feed feed = feedFacade.getFeedById(feedId);

		FeedMedium medium = feedMediumRepository
				.findTopByFeedOrderById(feed);
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
					.photoUrl(medium != null ?
							s3Util.getObjectUrl(medium.getFileName()) : null)
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

		return new JoinResponse(room.getId());
	}

	public List<CarrotRoomResponse> queryCarrotRoom() {
		return roomRepository.findByEmailAndType(userFacade.getCurrentEmail(),
				RoomType.CARROT)
				.stream().map(room ->
					new CarrotRoomResponse(room.getId(),
							room.getHeadUser().getName(),
							null, // TODO: 2021-10-03  
							room.getPhotoUrl())
				).collect(Collectors.toList());
	}

	public List<GroupRoomResponse> queryGroupRoom() {
		return roomRepository.findByEmailAndType(userFacade.getCurrentEmail(),
				RoomType.GROUP)
				.stream().map(room ->
						new GroupRoomResponse(room.getId(),
								room.getFeed().getTitle(),
								null, // TODO: 2021-10-03
								room.getPhotoUrl(),
								room.getFeed().getGroup()
										.getCurrentCount())
				).collect(Collectors.toList());
	}

	public List<QueryMessageResponse> queryMessage(QueryMessageRequest request, int page) {
		Room room = roomRepository.findById(request.getRoomId())
				.orElseThrow(RoomNotFoundException::new);
		User user = userFacade.getCurrentUser();

		if(memberRepository
				.findByUserAndRoom(user, room).isEmpty())
			throw new NotYourRoomException();

		return messageRepository.findByRoom(room, PageRequest.of(page, 10))
				.stream().map(message ->
					new QueryMessageResponse(message.getContent(),
							message.getType().name(), message.getMember().getUser().getEmail(),
							message.getMember().getUser().getName(),  message.getSentAt())
		).collect(Collectors.toList());
	}

	@Transactional
	public void leaveRoom(String roomId) {
		Room room = roomRepository.findById(roomId)
				.orElseThrow(RoomNotFoundException::new);
		User user = userFacade.getCurrentUser();

		if(memberRepository
				.findByUserAndRoom(user, room).isEmpty())
			throw new NotYourRoomException();

		if(room.isGroupRoom())
			room.getFeed().getGroup().decreaseCurrentCount();

		memberRepository.delete(memberRepository
				.findByUserAndRoom(user, room).get());
	}

}
