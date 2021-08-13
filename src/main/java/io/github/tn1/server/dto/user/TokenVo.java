package io.github.tn1.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenVo {
    private final String accessToken;
    private final String refreshToken;
}
