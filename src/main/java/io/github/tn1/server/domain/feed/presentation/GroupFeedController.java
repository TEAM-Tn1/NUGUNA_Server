package io.github.tn1.server.domain.feed.presentation;

import javax.validation.Valid;

import io.github.tn1.server.domain.feed.presentation.dto.request.ModifyGroupRequest;
import io.github.tn1.server.domain.feed.presentation.dto.request.PostGroupRequest;
import io.github.tn1.server.domain.feed.presentation.dto.response.PostFeedResponse;
import io.github.tn1.server.domain.feed.service.group.GroupFeedService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed/group")
@RequiredArgsConstructor
public class GroupFeedController {

	private final GroupFeedService groupFeedService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PostFeedResponse postGroupFeed(@RequestBody @Valid PostGroupRequest request) {
		return groupFeedService.postGroupFeed(request);
	}

	@PatchMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void modifyGroupFeed(@RequestBody @Valid ModifyGroupRequest request) {
		groupFeedService.modifyGroupFeed(request);
	}

}
