package io.github.tn1.server.dto.admin.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserBlackDateRequest {

	private LocalDate blackDate;

}
