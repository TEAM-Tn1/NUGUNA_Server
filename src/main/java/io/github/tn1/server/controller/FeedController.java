package io.github.tn1.server.controller;

import io.github.tn1.server.dto.feed.request.ModifyCarrotRequest;
import io.github.tn1.server.dto.feed.request.PostCarrotRequest;
import io.github.tn1.server.dto.feed.response.CarrotFeedResponse;
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

    @PatchMapping("/carrot")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyCarrotFeed(@RequestBody ModifyCarrotRequest request) {
        feedService.modifyCarrotFeed(request);
    }

    @GetMapping("/carrot")
    public List<CarrotFeedResponse> getCarrotFeed(@RequestParam("page") int page,
                                                  @RequestParam("range") int range) {
        return feedService.getCarrotFeed(page, range);
    }

    @DeleteMapping("/{feed_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFeed(@PathVariable(name = "feed_id") Long feedId) {
        feedService.removeFeed(feedId);
    }

    @GetMapping("/{email}")
    public List<WriteFeedResponse> getWriteFeed(@PathVariable("email") String email) {
        return feedService.getWriteFeed(email);
    }

}
