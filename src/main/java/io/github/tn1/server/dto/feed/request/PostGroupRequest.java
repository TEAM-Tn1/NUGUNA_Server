package io.github.tn1.server.dto.feed.request;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostGroupRequest {

	@NotEmpty(message = "title은 비어있으면 안됩니다.")
	private String title;

	private String description;

	@NotNull(message = "price는 null이면 안됩니다.")
	private Integer price;

	private List<String> tags;

	private Date date;

	@NotNull(message = "head_count는 null이면 안됩니다.")
	private Integer headCount;

}
