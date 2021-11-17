package io.github.tn1.server.domain.feed.service.carrot;

import javax.transaction.Transactional;

import io.github.tn1.server.domain.feed.presentation.dto.request.ModifyCarrotRequest;
import io.github.tn1.server.domain.feed.presentation.dto.request.PostCarrotRequest;
import io.github.tn1.server.domain.feed.domain.Feed;
import io.github.tn1.server.domain.feed.domain.repository.FeedRepository;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.domain.user.domain.repository.UserRepository;
import io.github.tn1.server.domain.feed.exception.NotYourFeedException;
import io.github.tn1.server.domain.feed.exception.TooManyTagsException;
import io.github.tn1.server.domain.feed.facade.FeedFacade;
import io.github.tn1.server.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CarrotFeedService {

	private final UserFacade userFacade;
	private final FeedFacade feedFacade;
	private final FeedRepository feedRepository;
	private final UserRepository userRepository;

	public void postCarrotFeed(PostCarrotRequest request) {
		if(request.getTags() != null && request.getTags().size() > 5)
			throw new TooManyTagsException();

		User user = userFacade.getCurrentUser();

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

	@Transactional
	public void modifyCarrotFeed(ModifyCarrotRequest request) {
		User user = userFacade.getCurrentUser();

		Feed feed = feedFacade.getFeedById(request.getFeedId());

		if(!feed.isWriter(user.getEmail()))
			throw new NotYourFeedException();

		feed
				.changeTitle(request.getTitle())
				.changeDescription(request.getDescription())
				.changePrice(request.getPrice());
	}

}
