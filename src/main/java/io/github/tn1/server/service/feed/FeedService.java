package io.github.tn1.server.service.feed;

import io.github.tn1.server.dto.feed.request.PostCarrotRequest;
import io.github.tn1.server.dto.feed.response.WriteFeedResponse;

import java.util.List;

public interface FeedService {
    void postCarrotFeed(PostCarrotRequest request);
    List<WriteFeedResponse> getWriteFeed(String email);
}
