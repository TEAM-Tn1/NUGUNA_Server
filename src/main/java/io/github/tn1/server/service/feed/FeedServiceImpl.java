package io.github.tn1.server.service.feed;

import io.github.tn1.server.dto.feed.request.PostCarrotRequest;
import io.github.tn1.server.dto.feed.response.WriteFeedResponse;
import io.github.tn1.server.entity.feed.FeedRepository;
import io.github.tn1.server.entity.feed.medium.FeedMedium;
import io.github.tn1.server.entity.feed.medium.FeedMediumRepository;
import io.github.tn1.server.entity.feed.tag.Tag;
import io.github.tn1.server.entity.feed.tag.TagRepository;
import io.github.tn1.server.entity.like.LikeRepository;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final LikeRepository likeRepository;
    private final FeedMediumRepository feedMediumRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    @Override
    public void postCarrotFeed(PostCarrotRequest request) {

    }

    @Override
    public List<WriteFeedResponse> getWriteFeed(String email) {
        User user = userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new);

        return feedRepository.findByUser(user)
                .stream().map(feed -> {
                    FeedMedium medium = feedMediumRepository
                            .findTopByFeedOrderById(feed);
                    WriteFeedResponse response = WriteFeedResponse.builder()
                            .feedId(feed.getId())
                            .title(feed.getTitle())
                            .description(feed.getDescription())
                            .price(feed.getPrice())
                            .tags(
                                    tagRepository.findByFeedOrderById(feed)
                                            .stream().map(Tag::getTag).collect(Collectors.toList()))
                            .photo(medium != null ? medium.getPath() : null)
                            .like(likeRepository.findByUserAndFeed(user, feed).isPresent())
                            .count(feed.getLikes().size())
                            .isUsedItem(feed.isUsedItem())
                            .build();
                    if (!feed.isUsedItem()) {
                        response.setGroupFeed(
                                feed.getGroup().getHeadCount(),
                                feed.getGroup().getRecruitmentDate()
                        );
                    }
                    return response;
                }).collect(Collectors.toList());
    }

}
