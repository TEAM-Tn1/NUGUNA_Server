package io.github.tn1.server.service.user;

import io.github.tn1.server.dto.user.request.LoginRequest;
import io.github.tn1.server.dto.user.request.RefreshTokenRequest;
import io.github.tn1.server.dto.user.response.OAuthLinkResponse;
import io.github.tn1.server.dto.user.response.TokenResponse;

public interface UserService {
    OAuthLinkResponse getOAuthLink();
    TokenResponse login(LoginRequest request);
    TokenResponse tokenRefresh(RefreshTokenRequest request);
}
