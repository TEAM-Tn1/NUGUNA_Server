package io.github.tn1.server.service.feed;

import java.util.List;

import io.github.tn1.server.dto.feed.request.ModifyTagRequest;
import io.github.tn1.server.dto.feed.response.FeedResponse;

import org.springframework.web.multipart.MultipartFile;

public interface FeedService {
    List<FeedResponse> queryWriteFeed(String email);
    FeedResponse queryFeed(Long feedId);
    void uploadPhoto(List<MultipartFile> files, Long feedId);
	void removeFeed(Long id);
    void modifyTag(ModifyTagRequest request);
}
