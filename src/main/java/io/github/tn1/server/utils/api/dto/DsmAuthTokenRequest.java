package io.github.tn1.server.utils.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DsmAuthTokenRequest {

    private final String clientId;
    private final String clientSecret;
    private final String code;

}
