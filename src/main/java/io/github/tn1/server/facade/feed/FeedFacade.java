package io.github.tn1.server.facade.feed;

import java.util.stream.Collectors;

import io.github.tn1.server.dto.feed.response.FeedResponse;
import io.github.tn1.server.entity.feed.Feed;
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
		FeedMedium medium = feedMediumRepository
				.findTopByFeedOrderById(feed);
		FeedResponse response = FeedResponse.WriteFeedResponseBuilder()
				.feedId(feed.getId())
				.title(feed.getTitle())
				.description(feed.getDescription())
				.price(feed.getPrice())
				.tags(
						tagRepository.findByFeedOrderById(feed)
								.stream().map(Tag::getTag).collect(Collectors.toList()))
				.photo(medium != null ? s3Util.getObjectUrl(medium.getFileName()) : null)
				.count(feed.getLikes().size())
				.lastModifyDate(feed.getUpdatedDate())
				.isUsedItem(feed.isUsedItem())
				.build();
		if (!feed.isUsedItem()) {
			response.setGroupFeed(
					feed.getGroup().getHeadCount(),
					feed.getGroup().getCurrentCount(),
					feed.getGroup().getRecruitmentDate()
			);
		}
		if(user != null)
			response.setLike(likeRepository.findByUserAndFeed(user, feed).isPresent());
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

}
