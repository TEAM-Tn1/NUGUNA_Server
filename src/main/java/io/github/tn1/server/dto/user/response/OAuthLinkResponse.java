package io.github.tn1.server.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthLinkResponse {
    private String link;
}
