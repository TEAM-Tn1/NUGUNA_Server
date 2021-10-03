package io.github.tn1.server.controller.feed;

import java.util.List;

import javax.validation.Valid;

import io.github.tn1.server.dto.feed.request.ModifyTagRequest;
import io.github.tn1.server.dto.feed.response.FeedResponse;
import io.github.tn1.server.dto.feed.response.TagResponse;
import io.github.tn1.server.service.feed.FeedService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

	private final FeedService feedService;

	@GetMapping("/users/{email}")
	public List<FeedResponse> getWriteFeed(@PathVariable("email") String email) {
		return feedService.queryWriteFeed(email);
	}

	@GetMapping("/{feed_id}")
	public FeedResponse getFeed(@PathVariable("feed_id") Long feedId) {
		return feedService.queryFeed(feedId);
	}

	@PostMapping("/{feed_id}/photo")
	@ResponseStatus(HttpStatus.CREATED)
	public void uploadPhoto(@RequestPart List<MultipartFile> files, @PathVariable("feed_id") Long feedId) {
		feedService.uploadPhoto(files, feedId);
	}

	@DeleteMapping("/{feed_id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeFeed(@PathVariable(name = "feed_id") Long feedId) {
		feedService.removeFeed(feedId);
	}

	@GetMapping("/{feed_id}/tags")
	public TagResponse queryTag(@PathVariable("feed_id") Long feedId) {
		return feedService.queryTag(feedId);
	}

	@PatchMapping("/tags")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeTag(@RequestBody @Valid ModifyTagRequest request) {
		feedService.modifyTag(request);
	}

	@PostMapping("/{feed_id}/like")
	@ResponseStatus(HttpStatus.CREATED)
	public void addLike(@PathVariable("feed_id") Long feedId) {
		feedService.addLike(feedId);
	}

	@DeleteMapping("/{feed_id}/like")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeLike(@PathVariable("feed_id") Long feedId) {
		feedService.removeLike(feedId);
	}

}
