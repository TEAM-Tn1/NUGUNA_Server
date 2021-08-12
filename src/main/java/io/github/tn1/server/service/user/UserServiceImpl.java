package io.github.tn1.server.service.user;

import io.github.tn1.server.dto.user.request.LoginRequest;
import io.github.tn1.server.dto.user.request.RefreshTokenRequest;
import io.github.tn1.server.dto.user.response.OAuthLinkResponse;
import io.github.tn1.server.dto.user.response.TokenResponse;
import io.github.tn1.server.entity.refresh_token.RefreshToken;
import io.github.tn1.server.entity.refresh_token.RefreshTokenRepository;
import io.github.tn1.server.entity.user.Role;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.UserNotFoundException;
import io.github.tn1.server.security.jwt.JwtTokenProvider;
import io.github.tn1.server.utils.api.client.DsmAuthClient;
import io.github.tn1.server.utils.api.dto.DsmAuthTokenRequest;
import io.github.tn1.server.utils.api.dto.InformationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${oauth.redirect-uri}")
    private String redirectUri;

    @Value("${oauth.client-id}")
    private String clientId;

    @Value("${oauth.secret}")
    private String clientSecret;

    @Value("${jwt.refresh.exp}")
    private Long refreshExp;

    private static String OAUTH_BASE_URI="https://developer.dsmkr.com/";

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final DsmAuthClient dsmAuthClient;

    @Override
    public OAuthLinkResponse getOAuthLink() {
        return new OAuthLinkResponse(OAUTH_BASE_URI + "external/login?redirect_url=" +
                redirectUri + "&client_id=" + clientId);
    }

    @Override
    public ResponseEntity<TokenResponse> login(LoginRequest request) {
        String accessToken = dsmAuthClient.getToken(
                DsmAuthTokenRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(request.getCode())
                .build()
        ).getAccessToken();
        InformationResponse response = dsmAuthClient
                .getInformation("Bearer " + accessToken);

        String refreshToken = jwtTokenProvider.generateRefreshToken(response.getEmail());
        refreshTokenRepository.findById(response.getEmail())
                .or(() -> Optional.of(new RefreshToken(response.getEmail(), refreshToken, refreshExp)))
                .ifPresent(token -> refreshTokenRepository.save(token.update(refreshToken, refreshExp)));

        HttpStatus status = HttpStatus.CREATED;

        if(userRepository.findById(response.getEmail()).isPresent()) {
            if(userRepository.save(
                    userRepository.findById(response.getEmail())
                            .orElseThrow(UserNotFoundException::new)
                            .changeNameAndGcn(response.getName(), response.getGcn())
            ).writeAllInformation())
                status = HttpStatus.OK;
        }

        userRepository.save(
                User.builder()
                .email(response.getEmail())
                .name(response.getName())
                .gcn(response.getGcn())
                .role(Role.ROLE_USER)
                .build()
        );

        return new ResponseEntity<>(new TokenResponse(
                jwtTokenProvider.generateAccessToken(response.getEmail()),
                refreshToken, response.getEmail()
        ), status);
    }

    @Override
    public TokenResponse tokenRefresh(RefreshTokenRequest request) {
        return null;
    }
}
