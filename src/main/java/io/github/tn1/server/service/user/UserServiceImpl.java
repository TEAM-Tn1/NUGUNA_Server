package io.github.tn1.server.service.user;

import io.github.tn1.server.dto.user.request.InformationRequest;
import io.github.tn1.server.dto.user.request.LoginRequest;
import io.github.tn1.server.dto.user.request.RefreshTokenRequest;
import io.github.tn1.server.dto.user.response.OAuthLinkResponse;
import io.github.tn1.server.dto.user.response.TokenResponse;
import io.github.tn1.server.dto.user.response.UserInformationResponse;
import io.github.tn1.server.entity.refresh_token.RefreshToken;
import io.github.tn1.server.entity.refresh_token.RefreshTokenRepository;
import io.github.tn1.server.entity.user.Role;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.ExpiredRefreshTokenException;
import io.github.tn1.server.exception.InvalidTokenException;
import io.github.tn1.server.exception.UserNotFoundException;
import io.github.tn1.server.security.facade.UserFacade;
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
    private final UserFacade userFacade;

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

        HttpStatus status = HttpStatus.CREATED;

        if(userRepository.findById(response.getEmail()).isPresent()) {
            if(userRepository.save(
                    userRepository.findById(response.getEmail())
                            .orElseThrow(UserNotFoundException::new)
                            .changeNameAndGcn(response.getName(), response.getGcn())
            ).writeAllInformation())
                status = HttpStatus.OK;
        } else {
            userRepository.save(
                    User.builder()
                            .email(response.getEmail())
                            .name(response.getName())
                            .gcn(response.getGcn())
                            .role(Role.ROLE_USER)
                            .build()
            );
        }
        
        TokenResponse token = getToken(response.getEmail());

        return new ResponseEntity<>(new TokenResponse(
                token.getAccessToken(),
                token.getRefreshToken(), token.getEmail()
        ), status);
    }

    @Override
    public TokenResponse tokenRefresh(RefreshTokenRequest request) {
        if (jwtTokenProvider.validateToken(request.getRefreshToken())){
            String email = jwtTokenProvider
                    .parseRefreshToken(request.getRefreshToken());
            return refreshTokenRepository.findById(email)
                    .map(token -> getToken(token.getEmail()))
                    .orElseThrow(ExpiredRefreshTokenException::new);
        }
        throw new InvalidTokenException();
    }

    @Override
    public void modifyInformation(InformationRequest request) {
        userRepository.findById(userFacade.getEmail())
                .map(user -> userRepository.save(
                        user.writeInformation(request.getRoomNumber(), request.getAccountNumber())
                ))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserInformationResponse getInformation(String email) {
        return userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new)
                .getInformation();
    }

    private TokenResponse getToken(String email) {
        String accessToken = jwtTokenProvider.generateAccessToken(email);
        String refreshToken = jwtTokenProvider.generateRefreshToken(email);
        refreshTokenRepository.findById(email)
                .or(() -> Optional.of(new RefreshToken(email, refreshToken, refreshExp)))
                .ifPresent(token -> refreshTokenRepository.save(token.update(refreshToken, refreshExp)));

        return new TokenResponse(accessToken, refreshToken, email);
    }

}
