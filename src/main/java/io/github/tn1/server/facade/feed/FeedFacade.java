package io.github.tn1.server.facade.feed;

import java.util.List;
import java.util.stream.Collectors;

import io.github.tn1.server.dto.feed.response.DefaultFeedResponse;
import io.github.tn1.server.dto.feed.response.FeedPreviewResponse;
import io.github.tn1.server.dto.feed.response.FeedResponse;
import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.feed.FeedRepository;
import io.github.tn1.server.entity.feed.medium.FeedMedium;
import io.github.tn1.server.entity.feed.medium.FeedMediumRepository;
import io.github.tn1.server.entity.feed.tag.Tag;
import io.github.tn1.server.entity.feed.tag.TagRepository;
import io.github.tn1.server.entity.like.LikeRepository;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.exception.FeedNotFoundException;
import io.github.tn1.server.utils.fcm.FcmUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedFacade {

	private final FcmUtil fcmUtil;
	private final LikeRepository likeRepository;
	private final FeedRepository feedRepository;
	private final FeedMediumRepository feedMediumRepository;
	private final TagRepository tagRepository;

	public Feed getFeedById(Long feedId) {
		return feedRepository.findById(feedId)
				.orElseThrow(FeedNotFoundException::new);
	}

	public FeedResponse feedToFeedResponse(Feed feed, User user) {
		DefaultFeedResponse defaultFeedResponse =
				getDefaultFeedResponse(feed, user);
		FeedResponse response = FeedResponse.builder()
				.defaultFeedResponse(defaultFeedResponse)
				.description(feed.getDescription())
				.photo(queryPhoto(feed))
				.lastModifyDate(feed.getUpdatedDate())
				.isUsedItem(feed.isUsedItem())
				.writerEmail(feed.getUser().getEmail())
				.writerName(feed.getUser().getName())
				.build();
		if (!feed.isUsedItem()) {
			response.setGroupFeed(
					feed.getGroup().getHeadCount(),
					feed.getGroup().getCurrentCount(),
					feed.getGroup().getRecruitmentDate()
			);
		}
		return response;
	}

	public FeedPreviewResponse feedToPreviewResponse(Feed feed, User user) {
		DefaultFeedResponse defaultFeedResponse =
				getDefaultFeedResponse(feed, user);
		FeedPreviewResponse response =
				FeedPreviewResponse.builder()
				.defaultFeedResponse(defaultFeedResponse)
				.medium(getFeedPhotoUrl(feed))
				.build();
		if (!feed.isUsedItem()) {
			response.setGroupInformation(
					feed.getGroup().getHeadCount(),
					feed.getGroup().getCurrentCount(),
					feed.getGroup().getRecruitmentDate()
			);
		}
		return response;
	}

	public void addTag(String tag, Feed feed) {
		tagRepository.save(
				Tag.builder()
						.feed(feed)
						.tag(tag)
						.build()
		);
		fcmUtil.sendTagNotification(tag, feed);
	}

	public List<String> queryTag(Feed feed) {
		return tagRepository.findByFeedOrderById(feed)
				.stream().map(Tag::getTag)
				.collect(Collectors.toList());
	}

	public List<String> queryPhoto(Feed feed) {
		return feedMediumRepository.findByFeedOrderById(feed)
				.stream().map(FeedMedium::getFileName)
				.collect(Collectors.toList());
	}

	public String getFeedPhotoUrl(Feed feed) {
		FeedMedium medium = feedMediumRepository
				.findTopByFeedOrderById(feed);

		return medium != null ? medium.getFileName() : null;
	}

	private DefaultFeedResponse getDefaultFeedResponse(Feed feed, User user) {

		DefaultFeedResponse response =
				DefaultFeedResponse.builder()
				.feedId(feed.getId())
				.title(feed.getTitle())
				.price(feed.getPrice())
				.count(feed.getCount())
				.tags(tagRepository.findByFeedOrderById(feed)
						.stream().map(Tag::getTag).collect(Collectors.toList()))
				.build();
		if(user != null) {
			response.setLike(likeRepository.findByUserAndFeed(user, feed)
					.isPresent());
		}
		return response;
	}

}
