package io.github.tn1.server.service.feed.carrot;

import java.util.List;

import io.github.tn1.server.dto.feed.request.ModifyCarrotRequest;
import io.github.tn1.server.dto.feed.request.PostCarrotRequest;
import io.github.tn1.server.dto.feed.response.FeedElementResponse;

public interface CarrotFeedService {
	void postCarrotFeed(PostCarrotRequest request);
	void modifyCarrotFeed(ModifyCarrotRequest request);
	List<FeedElementResponse> queryCarrotFeed(int page, int range);
	List<FeedElementResponse> queryLikedCarrot();
}
