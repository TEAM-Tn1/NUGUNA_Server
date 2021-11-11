package io.github.tn1.server.service.feed.group;

import java.time.LocalDate;

import javax.transaction.Transactional;

import io.github.tn1.server.dto.feed.request.ModifyGroupRequest;
import io.github.tn1.server.dto.feed.request.PostGroupRequest;
import io.github.tn1.server.entity.chat.member.Member;
import io.github.tn1.server.entity.chat.member.MemberRepository;
import io.github.tn1.server.entity.chat.room.Room;
import io.github.tn1.server.entity.chat.room.RoomRepository;
import io.github.tn1.server.entity.chat.room.RoomType;
import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.feed.FeedRepository;
import io.github.tn1.server.entity.feed.group.Group;
import io.github.tn1.server.entity.feed.group.GroupRepository;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.DateIsBeforeException;
import io.github.tn1.server.exception.FeedNotFoundException;
import io.github.tn1.server.exception.NotYourFeedException;
import io.github.tn1.server.exception.TooManyTagsException;
import io.github.tn1.server.facade.feed.FeedFacade;
import io.github.tn1.server.facade.user.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupFeedService {

	private static final RoomType GROUP_ROOM = RoomType.GROUP;

	private final UserFacade userFacade;
	private final FeedFacade feedFacade;
	private final UserRepository userRepository;
	private final FeedRepository feedRepository;
	private final GroupRepository groupRepository;
	private final RoomRepository roomRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void postGroupFeed(PostGroupRequest request) {

		if(request.getTags() != null && request.getTags().size() > 5)
			throw new TooManyTagsException();

		if(request.getDate().isBefore(LocalDate.now()))
			throw new DateIsBeforeException();

		User user = userFacade.getCurrentUser();

		Feed feed = feedRepository.save(
				Feed.builder()
				.title(request.getTitle())
				.description(request.getDescription())
				.price(request.getPrice())
				.user(user)
				.isUsedItem(false)
				.build()
		);

		groupRepository.save(
				Group.builder()
				.feed(feed)
				.headCount(request.getHeadCount())
				.recruitmentDate(request.getDate())
				.build()
		);

		if(request.getTags() != null)
			request.getTags().forEach(tag -> feedFacade.addTag(tag, feed));

		Room room = roomRepository.save(
				Room.builder()
				.feed(feed)
				.user(user)
				.photoUrl(null)
				.type(GROUP_ROOM)
				.build()
		);

		memberRepository.save(
				Member.builder()
				.room(room)
				.user(user)
				.build()
		);
	}

	@Transactional
	public void modifyGroupFeed(ModifyGroupRequest request) {
		User user = userFacade.getCurrentUser();

		Feed feed = feedFacade.getFeedById(request.getFeedId());

		if(!feed.isWriter(user.getEmail()))
			throw new NotYourFeedException();

		if(request.getDate().isBefore(LocalDate.now()))
			throw new DateIsBeforeException();

		feed
				.changeTitle(request.getTitle())
				.changePrice(request.getPrice())
				.changeDescription(request.getDescription());

		Group group = groupRepository.findByFeed(feed)
				.orElseThrow(FeedNotFoundException::new);
		group.changeHeadCount(request.getHeadCount());
		group.changeDate(request.getDate());
	}

}
