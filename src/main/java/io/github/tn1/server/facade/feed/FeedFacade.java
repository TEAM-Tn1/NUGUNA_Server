package io.github.tn1.server.facade.feed;

import java.util.List;
import java.util.stream.Collectors;

import io.github.tn1.server.dto.feed.response.CarrotResponse;
import io.github.tn1.server.dto.feed.response.DefaultFeedResponse;
import io.github.tn1.server.dto.feed.response.FeedPreviewResponse;
import io.github.tn1.server.dto.feed.response.FeedResponse;
import io.github.tn1.server.dto.feed.response.GroupResponse;
import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.feed.group.Group;
import io.github.tn1.server.entity.feed.medium.FeedMedium;
import io.github.tn1.server.entity.feed.medium.FeedMediumRepository;
import io.github.tn1.server.entity.feed.tag.Tag;
import io.github.tn1.server.entity.feed.tag.TagRepository;
import io.github.tn1.server.entity.like.LikeRepository;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.utils.fcm.FcmUtil;
import io.github.tn1.server.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedFacade {

	private final S3Util s3Util;
	private final FcmUtil fcmUtil;
	private final LikeRepository likeRepository;
	private final FeedMediumRepository feedMediumRepository;
	private final TagRepository tagRepository;

	public FeedResponse feedToFeedResponse(Feed feed, User user) {
		DefaultFeedResponse defaultFeedResponse =
				getDefaultFeedResponse(feed, user);
		FeedResponse response = FeedResponse.builder()
				.defaultFeedResponse(defaultFeedResponse)
				.description(feed.getDescription())
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

	public CarrotResponse feedToCarrotResponse(Feed feed, User user) {
		DefaultFeedResponse defaultFeedResponse =
				getDefaultFeedResponse(feed, user);
		return CarrotResponse.builder()
				.defaultFeedResponse(defaultFeedResponse)
				.build();
	}

	public GroupResponse feedToGroupResponse(Feed feed, User user) {
		DefaultFeedResponse defaultFeedResponse =
				getDefaultFeedResponse(feed, user);
		Group group = feed.getGroup();

		return GroupResponse.builder()
				.defaultFeedResponse(defaultFeedResponse)
				.currentHeadCount(group.getCurrentCount())
				.headCount(group.getHeadCount())
				.date(group.getRecruitmentDate())
				.build();
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

	public String getFeedPhotoUrl(Feed feed) {
		FeedMedium medium = feedMediumRepository
				.findTopByFeedOrderById(feed);

		return s3Util.getObjectUrl(medium.getFileName());
	}

	private DefaultFeedResponse getDefaultFeedResponse(Feed feed, User user) {
		FeedMedium medium = feedMediumRepository
				.findTopByFeedOrderById(feed);
		DefaultFeedResponse response =
				DefaultFeedResponse.builder()
				.feedId(feed.getId())
				.title(feed.getTitle())
				.price(feed.getPrice())
				.count(feed.getCount())
				.medium(medium != null ? s3Util.getObjectUrl(medium.getFileName()) : null)
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
