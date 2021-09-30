package io.github.tn1.server.service.feed;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.github.tn1.server.dto.feed.request.ModifyTagRequest;
import io.github.tn1.server.dto.feed.response.FeedResponse;
import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.feed.FeedRepository;
import io.github.tn1.server.entity.feed.medium.FeedMedium;
import io.github.tn1.server.entity.feed.medium.FeedMediumRepository;
import io.github.tn1.server.entity.feed.tag.Tag;
import io.github.tn1.server.entity.feed.tag.TagRepository;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.CredentialsNotFoundException;
import io.github.tn1.server.exception.FeedNotFoundException;
import io.github.tn1.server.exception.FileEmptyException;
import io.github.tn1.server.exception.MediumNotFoundException;
import io.github.tn1.server.exception.NotYourFeedException;
import io.github.tn1.server.exception.TooManyFilesException;
import io.github.tn1.server.exception.TooManyTagsException;
import io.github.tn1.server.exception.UserNotFoundException;
import io.github.tn1.server.security.facade.UserFacade;
import io.github.tn1.server.utils.feed.FeedUtil;
import io.github.tn1.server.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final S3Util s3Util;
    private final FeedRepository feedRepository;
    private final TagRepository tagRepository;
    private final FeedMediumRepository feedMediumRepository;
    private final UserRepository userRepository;



    @Override
    public List<FeedResponse> getWriteFeed(String email) {
        User writer = userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new);

        User user = userRepository.findById(UserFacade.getEmail())
                .orElse(null);

        return feedRepository.findByUser(writer)
                .stream().map(feed -> FeedUtil.feedToFeedResponse(feed, user))
				.collect(Collectors.toList());
    }

	@Override
	public FeedResponse getFeed(Long feedId) {
    	User user = userRepository.findById(UserFacade.getEmail())
				.orElse(null);
    	Feed feed = feedRepository.findById(feedId)
				.orElseThrow(FeedNotFoundException::new);
    	return FeedUtil.feedToFeedResponse(feed, user);
	}

	@Override
	public void uploadPhoto(List<MultipartFile> files, Long feedId) {
		User user = userRepository.findById(UserFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new);

		Feed feed = feedRepository.findById(feedId)
				.orElseThrow(FeedNotFoundException::new);

		if(!feed.getUser().getEmail().equals(user.getEmail()))
			throw new NotYourFeedException();

		if(files == null)
			throw new FileEmptyException();

		if(files.size() + feedMediumRepository.countByFeed(feed) > 5)
			throw new TooManyFilesException();

		files.forEach(file ->
				feedMediumRepository.save(FeedMedium.builder()
						.feed(feed)
						.fileName(s3Util.upload(file))
						.build())
		);
	}

	@Override
	public void removeFeed(Long id) {
		User user = userRepository.findById(UserFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new);

		Feed feed = feedRepository.findById(id)
				.orElseThrow(FeedNotFoundException::new);

		if(!feed.getUser().getEmail().equals(user.getEmail()))
			throw new NotYourFeedException();

		List<String> photoLinks = new ArrayList<>();

		for(FeedMedium medium : feed.getMedia()) {
			photoLinks.add(medium.getFileName());
		}

		photoLinks.forEach(this::removePhoto);

		feedRepository.deleteById(id);

	}

	@Override
	public void modifyTag(ModifyTagRequest request) {
		User user = userRepository.findById(UserFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new);

		Feed feed = feedRepository.findById(request.getFeedId())
				.orElseThrow(FeedNotFoundException::new);

		if(!feed.getUser().getEmail().equals(user.getEmail()))
			throw new NotYourFeedException();

		if(feed.getTags().size() + request.getTags().length > 5)
			throw new TooManyTagsException();

		for(String tag : request.getTags()) {
			tagRepository.save(
					Tag.builder()
							.feed(feed)
							.tag(tag)
					.build()
			);
		}
	}

	private void removePhoto(String fileName) {
		User user = userRepository.findById(UserFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new);

		FeedMedium medium = feedMediumRepository.findByFileName(fileName)
				.orElseThrow(MediumNotFoundException::new);

		if(!medium.getFeed().getUser().getEmail().equals(user.getEmail()))
			throw new NotYourFeedException();

		s3Util.delete(medium.getFileName());
		feedMediumRepository.delete(medium);
	}

}
