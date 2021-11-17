package io.github.tn1.server.domain.feed.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.github.tn1.server.domain.feed.domain.Feed;
import io.github.tn1.server.domain.feed.domain.FeedMedium;
import io.github.tn1.server.domain.feed.domain.repository.FeedMediumRepository;
import io.github.tn1.server.domain.feed.domain.repository.FeedRepository;
import io.github.tn1.server.domain.feed.domain.repository.TagRepository;
import io.github.tn1.server.domain.feed.exception.AlreadyLikedFeedException;
import io.github.tn1.server.domain.feed.exception.LikeNotFoundException;
import io.github.tn1.server.domain.feed.exception.MediumNotFoundException;
import io.github.tn1.server.domain.feed.exception.NotYourFeedException;
import io.github.tn1.server.domain.feed.exception.TooManyTagsException;
import io.github.tn1.server.domain.feed.facade.FeedFacade;
import io.github.tn1.server.domain.feed.presentation.dto.request.ModifyTagRequest;
import io.github.tn1.server.domain.feed.presentation.dto.response.FeedPreviewResponse;
import io.github.tn1.server.domain.feed.presentation.dto.response.FeedResponse;
import io.github.tn1.server.domain.feed.presentation.dto.response.TagResponse;
import io.github.tn1.server.domain.like.domain.Like;
import io.github.tn1.server.domain.like.domain.repository.LikeRepository;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.domain.user.domain.repository.UserRepository;
import io.github.tn1.server.domain.user.exception.UserNotFoundException;
import io.github.tn1.server.domain.user.facade.UserFacade;
import io.github.tn1.server.global.exception.FileEmptyException;
import io.github.tn1.server.global.exception.TooManyFilesException;
import io.github.tn1.server.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FeedService {

	private static final Integer MAX_FILE_COUNT = 5;

	private final UserFacade userFacade;
	private final FeedFacade feedFacade;
    private final S3Util s3Util;
    private final FeedRepository feedRepository;
    private final TagRepository tagRepository;
    private final FeedMediumRepository feedMediumRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

	public FeedResponse queryFeed(Long feedId) {
    	User user = userRepository.findById(userFacade.getCurrentEmail())
				.orElse(null);

    	Feed feed = feedFacade.getFeedById(feedId);

    	return feedFacade.feedToFeedResponse(feed, user);
	}

	@Transactional
	public void uploadPhoto(List<MultipartFile> files, Long feedId) {
		User user = userFacade.getCurrentUser();

		Feed feed = feedFacade.getFeedById(feedId);

		if(!feed.isWriter(user.getEmail()))
			throw new NotYourFeedException();

		if(files == null)
			throw new FileEmptyException();

		if(files.size() + feedMediumRepository.countByFeed(feed) > MAX_FILE_COUNT)
			throw new TooManyFilesException();

		files.forEach(file ->
				feedMediumRepository.save(FeedMedium.builder()
						.feed(feed)
						.fileName(s3Util.upload(file))
						.build())
		);

		addRoomPhoto(feed);
	}

	@Transactional
	public void removeFeed(Long feedId) {
		User user = userFacade.getCurrentUser();

		Feed feed = feedFacade.getFeedById(feedId);

		if(!feed.isWriter(user.getEmail()))
			throw new NotYourFeedException();

		feedRepository.deleteById(feedId);
	}

	public TagResponse queryTag(Long feedId) {
    	Feed feed = feedFacade.getFeedById(feedId);

    	return new TagResponse(
				feedFacade.queryTag(feed)
		);
	}

	@Transactional
	public void modifyTag(ModifyTagRequest request) {
		User user = userFacade.getCurrentUser();

		Feed feed = feedFacade.getFeedById(request.getFeedId());

		if(!feed.isWriter(user.getEmail()))
			throw new NotYourFeedException();

		if(request.getTags().size() > 5)
			throw new TooManyTagsException();

		tagRepository.deleteByFeed(feed);

		for(String tag : request.getTags()) {
			feedFacade.addTag(tag, feed);
		}
	}

	@Transactional
	public void addLike(Long feedId) {
		User user = userFacade.getCurrentUser();

    	Feed feed = feedFacade.getFeedById(feedId);

    	try {
			likeRepository.save(
					Like.builder()
							.user(user)
							.feed(feed)
							.build()
			);
			feed.increaseCount();
		} catch (RuntimeException e) {
    		throw new AlreadyLikedFeedException();
		}
	}

	@Transactional
	public void removeLike(Long feedId) {
		User user = userFacade.getCurrentUser();

		Feed feed = feedFacade.getFeedById(feedId);

		likeRepository.delete(
				likeRepository
						.findByUserAndFeed(user, feed)
						.orElseThrow(LikeNotFoundException::new)
		);
		feed.decreaseCount();
	}

	public List<FeedPreviewResponse> queryFeed(int page, int range, String sort, boolean isUsedItem) {
		User user = userRepository.findById(userFacade.getCurrentEmail())
				.orElse(null);

		PageRequest pageRequest;

		if(sort != null && sort.equals("like")) {
			pageRequest = PageRequest.of(page, range, Sort.by("count").descending().and(Sort.by("id")));
		} else pageRequest = PageRequest.of(page, range, Sort.by("id").descending());

		return feedRepository.findByIsUsedItem(isUsedItem,
				pageRequest)
				.stream().filter(feed -> feed.getGroup() == null ||
						feed.getGroup().getCurrentCount() < feed.getGroup().getHeadCount())
				.map(feed ->
						feedFacade.feedToPreviewResponse(feed, user)
				).collect(Collectors.toList());
	}

	public List<FeedPreviewResponse> queryLikeFeed(boolean isUsedItem, Pageable pageable) {
		User user = userFacade.getCurrentUser();

		return likeRepository.findByUser(user, pageable)
				.stream().filter(like -> like.getFeed().isUsedItem() == isUsedItem)
				.map(like ->
						feedFacade.feedToPreviewResponse(like.getFeed(), user)
				).collect(Collectors.toList());
	}

	public List<FeedPreviewResponse> querySpecificUserFeed(String email, boolean isUsedItem, Pageable pageable) {
		User currentUser = userRepository.findById(userFacade.getCurrentEmail())
				.orElse(null);
		User user = userRepository.findById(email)
				.orElseThrow(UserNotFoundException::new);
		return feedRepository.findByUserAndIsUsedItem(user, isUsedItem, pageable)
				.stream().map(feed ->
						feedFacade.feedToPreviewResponse(feed, currentUser)
				).collect(Collectors.toList());
	}

	public List<FeedPreviewResponse> queryFeedByTitle(String title, boolean isUsedItem, Pageable pageable) {
		User currentUser = userRepository.findById(userFacade.getCurrentEmail())
				.orElse(null);
		return feedRepository.findByTitleContainsAndIsUsedItem(title, isUsedItem, pageable)
				.stream().map(feed ->
						feedFacade.feedToPreviewResponse(feed, currentUser)
				).collect(Collectors.toList());
	}

	private void removePhoto(String fileName) {
		User user = userFacade.getCurrentUser();

		FeedMedium medium = feedMediumRepository.findByFileName(fileName)
				.orElseThrow(MediumNotFoundException::new);

		if(!medium.getFeed().isWriter(user.getEmail()))
			throw new NotYourFeedException();

		s3Util.delete(medium.getFileName());

		feedMediumRepository.delete(medium);
	}

	private void addRoomPhoto(Feed feed) {
    	String photoUrl = feedFacade.getFeedPhotoUrl(feed);

    	feed.getRoom().forEach(room -> {
    		if(room.getPhotoUrl() == null)
    			room.initPhotoUrl(photoUrl);
		});
	}

}
