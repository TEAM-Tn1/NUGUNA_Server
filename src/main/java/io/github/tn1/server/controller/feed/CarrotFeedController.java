package io.github.tn1.server.controller.feed;

import java.util.List;

import io.github.tn1.server.dto.feed.request.ModifyCarrotRequest;
import io.github.tn1.server.dto.feed.request.PostCarrotRequest;
import io.github.tn1.server.dto.feed.response.FeedElementResponse;
import io.github.tn1.server.service.feed.carrot.CarrotFeedService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class CarrotFeedController {

    private final CarrotFeedService feedService;

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
    public List<FeedElementResponse> getCarrotFeed(@RequestParam("page") int page,
                                                  @RequestParam("range") int range) {
        return feedService.getCarrotFeed(page, range);
    }

    @GetMapping("/me/like/carrot")
	public List<FeedElementResponse> getLikedCarrot() {
    	return feedService.getLikedCarrot();
	}

}