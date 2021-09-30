package io.github.tn1.server.dto.user.request;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenRequest {
	@NotEmpty(message = "refresh_token은 비어있으면 안됩니다.")
    private String refreshToken;
}
