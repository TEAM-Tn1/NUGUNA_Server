package io.github.tn1.server.service.feed;

import java.util.List;

import io.github.tn1.server.dto.feed.response.FeedResponse;

import org.springframework.web.multipart.MultipartFile;

public interface FeedService {
    void removeFeed(Long id);
    List<FeedResponse> getWriteFeed(String email);
    void uploadPhoto(List<MultipartFile> files, Long feedId);
    void removePhoto(String path);
}
