package io.github.tn1.server.domain.user.presentation.dto.admin.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportInformationResponse {

	private final String description;
	private final String photoUrl;

}
