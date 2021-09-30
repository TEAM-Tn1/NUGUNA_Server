package io.github.tn1.server.service.feed.carrot;

import java.util.List;
import java.util.stream.Collectors;

import io.github.tn1.server.dto.feed.request.ModifyCarrotRequest;
import io.github.tn1.server.dto.feed.request.PostCarrotRequest;
import io.github.tn1.server.dto.feed.response.FeedElementResponse;
import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.feed.FeedRepository;
import io.github.tn1.server.entity.feed.medium.FeedMedium;
import io.github.tn1.server.entity.feed.medium.FeedMediumRepository;
import io.github.tn1.server.entity.feed.tag.Tag;
import io.github.tn1.server.entity.feed.tag.TagRepository;
import io.github.tn1.server.entity.like.LikeRepository;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.CredentialsNotFoundException;
import io.github.tn1.server.exception.FeedNotFoundException;
import io.github.tn1.server.exception.NotYourFeedException;
import io.github.tn1.server.exception.TooManyTagsException;
import io.github.tn1.server.exception.UserNotFoundException;
import io.github.tn1.server.security.facade.UserFacade;
import io.github.tn1.server.utils.fcm.FcmUtil;
import io.github.tn1.server.utils.feed.FeedUtil;
import io.github.tn1.server.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CarrotFeedServiceImpl implements CarrotFeedService {

	private final FcmUtil fcmUtil;
	private final S3Util s3Util;
	private final FeedRepository feedRepository;
	private final LikeRepository likeRepository;
	private final FeedMediumRepository feedMediumRepository;
	private final TagRepository tagRepository;
	private final UserRepository userRepository;

	@Override
	public void postCarrotFeed(PostCarrotRequest request) {

		if(request.getTags() != null && request.getTags().size() > 5)
			throw new TooManyTagsException();

		User user = userRepository.findById(UserFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new);

		Feed feed = feedRepository.save(
				Feed.builder()
						.title(request.getTitle())
						.description(request.getDescription())
						.price(request.getPrice())
						.user(user)
						.isUsedItem(true)
						.build()
		);


		request.getTags().forEach(tag ->{
					tagRepository.save(
							Tag.builder()
									.feed(feed)
									.tag(tag)
									.build()
					);
					fcmUtil.sendTagNotification(tag, feed);
				}
		);

	}

	@Override
	public void modifyCarrotFeed(ModifyCarrotRequest request) {
		User user = userRepository.findById(UserFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new);

		Feed feed = feedRepository.findById(request.getFeedId())
				.orElseThrow(FeedNotFoundException::new);

		if(!feed.getUser().getEmail().equals(user.getEmail()))
			throw new NotYourFeedException();

		feed
				.setTitle(request.getTitle())
				.setDescription(request.getDescription())
				.setPrice(request.getPrice());

		feedRepository.save(feed);
	}

	@Override
	public List<FeedElementResponse> queryCarrotFeed(int page, int range) {

		User user = userRepository.findById(UserFacade.getEmail())
				.orElse(null);

		return feedRepository.findByIsUsedItem(true, PageRequest.of(page, range, Sort.by("id").descending()))
				.stream()
				.map(feed -> {
					FeedMedium medium = feedMediumRepository
							.findTopByFeedOrderById(feed);
					FeedElementResponse response;
					response = FeedElementResponse.builder()
							.feedId(feed.getId())
							.title(feed.getTitle())
							.price(feed.getPrice())
							.lastModifyDate(feed.getUpdatedDate())
							.count(feed.getLikes().size())
							.description(feed.getDescription())
							.medium(medium != null ? s3Util.getObjectUrl(medium.getFileName()) : null)
							.tags(tagRepository.findByFeedOrderById(feed)
									.stream().map(Tag::getTag).collect(Collectors.toList()))
							.build();
					if(user != null) {
						response.setLike(likeRepository.findByUserAndFeed(user, feed)
								.isPresent());
					}
					return response;
				}).collect(Collectors.toList());
	}

	@Override
	public List<FeedElementResponse> queryLikedCarrot() {
		User user = userRepository.findById(UserFacade.getEmail())
				.orElseThrow(UserNotFoundException::new);
		return user.getLikes()
				.stream().filter(like -> like.getFeed().isUsedItem())
				.map(like -> {
					Feed feed = like.getFeed();
					return FeedUtil.feedToFeedResponse(feed, user);
				}).collect(Collectors.toList());
	}

}
