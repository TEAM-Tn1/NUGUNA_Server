package io.github.tn1.server.service.chat;

import java.util.List;
import java.util.stream.Collectors;

import io.github.tn1.server.dto.chat.response.CarrotRoomResponse;
import io.github.tn1.server.dto.chat.response.JoinResponse;
import io.github.tn1.server.entity.chat.member.Member;
import io.github.tn1.server.entity.chat.member.MemberRepository;
import io.github.tn1.server.entity.chat.room.Room;
import io.github.tn1.server.entity.chat.room.RoomRepository;
import io.github.tn1.server.entity.chat.room.RoomType;
import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.feed.FeedRepository;
import io.github.tn1.server.entity.feed.medium.FeedMedium;
import io.github.tn1.server.entity.feed.medium.FeedMediumRepository;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.AlreadyJoinRoomException;
import io.github.tn1.server.exception.FeedNotFoundException;
import io.github.tn1.server.exception.RoomNotFoundException;
import io.github.tn1.server.exception.UserNotFoundException;
import io.github.tn1.server.security.facade.UserFacade;
import io.github.tn1.server.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final UserRepository userRepository;
	private final FeedRepository feedRepository;
	private final FeedMediumRepository feedMediumRepository;
	private final RoomRepository roomRepository;
	private final MemberRepository memberRepository;

	private final S3Util s3Util;

	public JoinResponse joinRoom(Long feedId) {
		User currentUser = userRepository.findById(UserFacade.getEmail())
				.orElseThrow(UserNotFoundException::new);
		Feed feed = feedRepository.findById(feedId)
				.orElseThrow(FeedNotFoundException::new);
		FeedMedium medium = feedMediumRepository
				.findTopByFeedOrderById(feed);
		Room room;
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
		} else {
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

	public List<CarrotRoomResponse> getCarrotRoom() {
		return roomRepository.findByEmailAndType(UserFacade.getEmail(),
				RoomType.CARROT)
				.stream().map(room ->
					new CarrotRoomResponse(room.getId(),
							room.getHeadUser().getName(),
							null,
							room.getPhotoUrl())
				).collect(Collectors.toList());
	}

}
