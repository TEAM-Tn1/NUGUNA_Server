package io.github.tn1.server.controller.feed;

import java.util.List;

import javax.validation.Valid;

import io.github.tn1.server.dto.feed.request.ModifyGroupRequest;
import io.github.tn1.server.dto.feed.request.PostGroupRequest;
import io.github.tn1.server.dto.feed.response.GroupResponse;
import io.github.tn1.server.service.feed.group.GroupFeedService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class GroupFeedController {

	private final GroupFeedService groupFeedService;

	@PostMapping("/group")
	@ResponseStatus(HttpStatus.CREATED)
	public void postGroupFeed(@RequestBody @Valid PostGroupRequest request) {
		groupFeedService.postGroupFeed(request);
	}

	@PatchMapping("/group")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void modifyGroupFeed(@RequestBody @Valid ModifyGroupRequest request) {
		groupFeedService.modifyGroupFeed(request);
	}

	@GetMapping("/users/{email}/group")
	public List<GroupResponse> querySpecificUserGroup(@PathVariable("email") String email) {
		return groupFeedService.querySpecificUserGroupFeed(email);
	}

}
