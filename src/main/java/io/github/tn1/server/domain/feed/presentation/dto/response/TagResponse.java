package io.github.tn1.server.domain.feed.presentation.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagResponse {

	private final List<String> tags;

}
