package io.github.tn1.server.controller.feed;

import java.util.List;

import io.github.tn1.server.dto.feed.response.FeedResponse;
import io.github.tn1.server.service.feed.FeedService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		return feedService.getWriteFeed(email);
	}

	@GetMapping("/{feed_id}")
	public FeedResponse getFeed(@PathVariable("feed_id") Long feedId) {
		return feedService.getFeed(feedId);
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

	@DeleteMapping("/photo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePhoto(@RequestParam("file_name") String fileName) {
		feedService.removePhoto(fileName);
	}

}
