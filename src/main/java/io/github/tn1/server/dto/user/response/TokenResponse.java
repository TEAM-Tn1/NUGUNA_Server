package io.github.tn1.server.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {
    private final String accessToken;
    private final String refreshToken;
    private final String email;
}
