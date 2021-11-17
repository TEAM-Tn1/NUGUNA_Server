package io.github.tn1.server.domain.feed.presentation;

import java.util.List;

import javax.validation.Valid;

import io.github.tn1.server.domain.feed.presentation.dto.request.ModifyTagRequest;
import io.github.tn1.server.domain.feed.presentation.dto.response.FeedPreviewResponse;
import io.github.tn1.server.domain.feed.presentation.dto.response.FeedResponse;
import io.github.tn1.server.domain.feed.presentation.dto.response.TagResponse;
import io.github.tn1.server.domain.feed.service.FeedService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping
	public List<FeedPreviewResponse> queryFeedList(@RequestParam("page") int page,
			@RequestParam("range") int range, @RequestParam("sort") @Nullable String sort,
			@RequestParam("is_used_item") boolean isUsedItem) {
		return feedService.queryFeed(page, range, sort, isUsedItem);
	}

	@GetMapping("/me/like")
	public List<FeedPreviewResponse> queryLikeFeedList(Pageable pageable,
			@RequestParam("is_used_item") boolean isUsedItem) {
		return feedService.queryLikeFeed(isUsedItem, pageable);
	}

	@GetMapping("/{feed_id}")
	public FeedResponse getFeed(@PathVariable("feed_id") Long feedId) {
		return feedService.queryFeed(feedId);
	}

	@GetMapping("/{feed_id}/tags")
	public TagResponse queryTag(@PathVariable("feed_id") Long feedId) {
		return feedService.queryTag(feedId);
	}

	@GetMapping("/users/{email}")
	public List<FeedPreviewResponse> querySpecificUserFeed(Pageable pageable,
			@PathVariable("email") String email,
			@RequestParam("is_used_item") boolean isUsedItem) {
		return feedService.querySpecificUserFeed(email, isUsedItem, pageable);
	}

	@GetMapping("/search")
	public List<FeedPreviewResponse> queryFeedByTitle(Pageable pageable,
			@RequestParam("title") String title,
			@RequestParam("is_used_item") boolean isUsedItem) {
		return feedService.queryFeedByTitle(title, isUsedItem, pageable);
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
