package io.github.tn1.server.dto.feed.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCarrotRequest {

	@Size(max = 30, message = "title은 30자를 넘어서는 안됩니다.")
	@NotBlank(message = "title은 비어있으면 안됩니다.")
    private String title;

	@Size(max = 1000, message = "description은 1000자를 넘어서는 안됩니다.")
    private String description;

	@NotNull(message = "price는 null이면 안됩니다.")
    private Integer price;

    private List<@Size(max = 10, message = "tag는 10자를 넘어서는 안됩니다.") String> tags;

}
