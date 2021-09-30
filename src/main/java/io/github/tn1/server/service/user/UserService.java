package io.github.tn1.server.service.user;

import io.github.tn1.server.dto.user.request.DeviceTokenRequest;
import io.github.tn1.server.dto.user.request.InformationRequest;
import io.github.tn1.server.dto.user.request.LoginRequest;
import io.github.tn1.server.dto.user.request.RefreshTokenRequest;
import io.github.tn1.server.dto.user.response.AccountResponse;
import io.github.tn1.server.dto.user.response.OAuthLinkResponse;
import io.github.tn1.server.dto.user.response.TokenResponse;
import io.github.tn1.server.dto.user.response.UserInformationResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    OAuthLinkResponse getOAuthLink();
    ResponseEntity<TokenResponse> login(LoginRequest request);
    TokenResponse tokenRefresh(RefreshTokenRequest request);
    void modifyInformation(InformationRequest request);
    UserInformationResponse getInformation(String email);
    AccountResponse getAccount();
    void insertDeviceToken(DeviceTokenRequest request);
    void logout();
}
