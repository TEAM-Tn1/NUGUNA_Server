package io.github.tn1.server.controller.feed;

import javax.validation.Valid;

import io.github.tn1.server.dto.feed.request.ModifyCarrotRequest;
import io.github.tn1.server.dto.feed.request.PostCarrotRequest;
import io.github.tn1.server.service.feed.carrot.CarrotFeedService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed/carrot")
@RequiredArgsConstructor
public class CarrotFeedController {

    private final CarrotFeedService feedService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postCarrotFeed(@RequestBody @Valid PostCarrotRequest request) {
        feedService.postCarrotFeed(request);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyCarrotFeed(@RequestBody @Valid ModifyCarrotRequest request) {
        feedService.modifyCarrotFeed(request);
    }

}
