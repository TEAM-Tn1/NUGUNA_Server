package io.github.tn1.server.service.user;

import io.github.tn1.server.dto.user.request.InformationRequest;
import io.github.tn1.server.dto.user.request.LoginRequest;
import io.github.tn1.server.dto.user.request.RefreshTokenRequest;
import io.github.tn1.server.dto.user.response.OAuthLinkResponse;
import io.github.tn1.server.dto.user.response.TokenResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    OAuthLinkResponse getOAuthLink();
    ResponseEntity<TokenResponse> login(LoginRequest request);
    TokenResponse tokenRefresh(RefreshTokenRequest request);
    void modifyInformation(InformationRequest request);
}
