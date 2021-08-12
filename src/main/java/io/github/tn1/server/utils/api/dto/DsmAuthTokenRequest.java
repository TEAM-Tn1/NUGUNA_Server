package io.github.tn1.server.utils.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DsmAuthTokenRequest {

    private String clientId;
    private String clientSecret;
    private String code;

}
