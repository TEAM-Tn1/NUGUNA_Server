package io.github.tn1.server.controller;

import io.github.tn1.server.dto.feed.request.PostCarrotRequest;
import io.github.tn1.server.dto.feed.response.WriteFeedResponse;
import io.github.tn1.server.service.feed.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping("/carrot")
    @ResponseStatus(HttpStatus.CREATED)
    public void postCarrotFeed(@RequestBody PostCarrotRequest request) {
        feedService.postCarrotFeed(request);
    }

    @GetMapping("/{email}")
    public List<WriteFeedResponse> getWriteFeed(@PathVariable("email") String email) {
        return feedService.getWriteFeed(email);
    }

}
