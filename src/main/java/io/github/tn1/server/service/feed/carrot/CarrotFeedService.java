package io.github.tn1.server.service.feed.carrot;

import java.util.List;
import java.util.stream.Collectors;

import io.github.tn1.server.dto.feed.request.ModifyCarrotRequest;
import io.github.tn1.server.dto.feed.request.PostCarrotRequest;
import io.github.tn1.server.dto.feed.response.CarrotResponse;
import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.feed.FeedRepository;
import io.github.tn1.server.entity.feed.medium.FeedMediumRepository;
import io.github.tn1.server.entity.feed.tag.TagRepository;
import io.github.tn1.server.entity.like.LikeRepository;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.CredentialsNotFoundException;
import io.github.tn1.server.exception.FeedNotFoundException;
import io.github.tn1.server.exception.NotYourFeedException;
import io.github.tn1.server.exception.TooManyTagsException;
import io.github.tn1.server.exception.UserNotFoundException;
import io.github.tn1.server.facade.feed.FeedFacade;
import io.github.tn1.server.facade.user.UserFacade;
import io.github.tn1.server.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CarrotFeedService {

	private final UserFacade userFacade;
	private final FeedFacade feedFacade;
	private final S3Util s3Util;
	private final FeedRepository feedRepository;
	private final LikeRepository likeRepository;
	private final FeedMediumRepository feedMediumRepository;
	private final TagRepository tagRepository;
	private final UserRepository userRepository;

	public void postCarrotFeed(PostCarrotRequest request) {

		if(request.getTags() != null && request.getTags().size() > 5)
			throw new TooManyTagsException();

		User user = userRepository.findById(userFacade.getEmail())
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

		if(request.getTags() != null)
			request.getTags().forEach(tag -> feedFacade.addTag(tag, feed));

	}

	public void modifyCarrotFeed(ModifyCarrotRequest request) {
		User user = userRepository.findById(userFacade.getEmail())
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

	public List<CarrotResponse> queryCarrotFeed(int page, int range) {

		User user = userRepository.findById(userFacade.getEmail())
				.orElse(null);

		return feedRepository.findByIsUsedItem(true, PageRequest.of(page, range, Sort.by("id").descending()))
				.stream()
				.map(feed ->
					feedFacade.feedToCarrotResponse(feed, user)
				).collect(Collectors.toList());
	}

	public List<CarrotResponse> queryLikedCarrot() {
		User user = userRepository.findById(userFacade.getEmail())
				.orElseThrow(UserNotFoundException::new);
		return user.getLikes()
				.stream().filter(like -> like.getFeed().isUsedItem())
				.map(like ->
						feedFacade.feedToCarrotResponse(like.getFeed(), user)
				).collect(Collectors.toList());
	}

}
