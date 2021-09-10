package io.github.tn1.server.service.feed;

import io.github.tn1.server.dto.feed.request.ModifyCarrotRequest;
import io.github.tn1.server.dto.feed.request.PostCarrotRequest;
import io.github.tn1.server.dto.feed.response.FeedResponse;
import io.github.tn1.server.dto.feed.response.WriteFeedResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FeedService {
    void postCarrotFeed(PostCarrotRequest request);
    void modifyCarrotFeed(ModifyCarrotRequest request);
    List<FeedResponse> getCarrotFeed(int page, int range);
    void removeFeed(Long id);
    List<WriteFeedResponse> getWriteFeed(String email);
    void uploadPhoto(List<MultipartFile> files, Long feedId);
    List<FeedResponse> getLikedCarrot();
    void removePhoto(String path);
}
