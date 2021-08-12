package io.github.tn1.server.service.user;

import io.github.tn1.server.dto.user.request.LoginRequest;
import io.github.tn1.server.dto.user.request.RefreshTokenRequest;
import io.github.tn1.server.dto.user.response.OAuthLinkResponse;
import io.github.tn1.server.dto.user.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Value("${oauth.redirect-uri}")
    private String redirectUri;

    @Value("${oauth.client-id}")
    private String clientId;

    @Value("${oauth.secret}")
    private String serviceSecret;

    private static String OAUTH_BASE_URI="https://developer.dsmkr.com/";

    @Override
    public OAuthLinkResponse getOAuthLink() {
        return new OAuthLinkResponse(OAUTH_BASE_URI + "external/login?redirect_url=" +
                redirectUri + "&client_id=" + clientId);
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public TokenResponse tokenRefresh(RefreshTokenRequest request) {
        return null;
    }
}
