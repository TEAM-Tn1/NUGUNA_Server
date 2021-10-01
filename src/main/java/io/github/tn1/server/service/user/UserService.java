package io.github.tn1.server.service.user;

import java.util.Optional;

import javax.transaction.Transactional;

import io.github.tn1.server.dto.user.request.DeviceTokenRequest;
import io.github.tn1.server.dto.user.request.InformationRequest;
import io.github.tn1.server.dto.user.request.LoginRequest;
import io.github.tn1.server.dto.user.request.RefreshTokenRequest;
import io.github.tn1.server.dto.user.response.AccountResponse;
import io.github.tn1.server.dto.user.response.OAuthLinkResponse;
import io.github.tn1.server.dto.user.response.RoomNumberResponse;
import io.github.tn1.server.dto.user.response.TokenResponse;
import io.github.tn1.server.dto.user.response.UserInformationResponse;
import io.github.tn1.server.entity.refresh_token.RefreshToken;
import io.github.tn1.server.entity.refresh_token.RefreshTokenRepository;
import io.github.tn1.server.entity.user.Role;
import io.github.tn1.server.entity.user.User;
import io.github.tn1.server.entity.user.UserRepository;
import io.github.tn1.server.exception.CredentialsNotFoundException;
import io.github.tn1.server.exception.ExpiredRefreshTokenException;
import io.github.tn1.server.exception.InvalidTokenException;
import io.github.tn1.server.exception.UserNotFoundException;
import io.github.tn1.server.facade.user.UserFacade;
import io.github.tn1.server.security.jwt.JwtTokenProvider;
import io.github.tn1.server.utils.api.client.DsmAuthClient;
import io.github.tn1.server.utils.api.dto.DsmAuthTokenRequest;
import io.github.tn1.server.utils.api.dto.InformationResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${oauth.redirect-uri}")
    private String redirectUri;

    @Value("${oauth.client-id}")
    private String clientId;

    @Value("${oauth.secret}")
    private String clientSecret;

    @Value("${jwt.refresh.exp}")
    private Long refreshExp;

    private static final String OAUTH_BASE_URI="https://dsm-auth.vercel.app/";

    private final UserFacade userFacade;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final DsmAuthClient dsmAuthClient;

    public OAuthLinkResponse queryOAuthLink() {
        return new OAuthLinkResponse(OAUTH_BASE_URI + "external/login?redirect_url=" +
                redirectUri + "&client_id=" + clientId);
    }

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

    public UserInformationResponse queryUserInformation() {
    	return userRepository.findById(userFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new)
				.getInformation(userFacade.getEmail());
	}

    public void modifyInformation(InformationRequest request) {
        userRepository.findById(userFacade.getEmail())
                .map(user -> userRepository.save(
                        user.writeInformation(request.getRoomNumber(), request.getAccountNumber())
                ))
                .orElseThrow(CredentialsNotFoundException::new);
    }

    public UserInformationResponse queryInformation(String email) {
        return userRepository.findById(email)
                .orElseThrow(UserNotFoundException::new)
                .getInformation(userFacade.getEmail());
    }

	public AccountResponse queryAccount() {
		return new AccountResponse(userRepository
				.findById(userFacade.getEmail())
				.map(User::getAccountNumber)
				.orElse(null));
	}

	@Transactional
	public void insertDeviceToken(DeviceTokenRequest request) {
		userRepository
				.findById(userFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new)
				.changeDeviceToken(request.getDeviceToken());
	}

	@Transactional
	public void logout() {
		userRepository
				.findById(userFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new)
				.removeDeviceToken();
		refreshTokenRepository.findById(userFacade.getEmail())
				.ifPresent(refreshTokenRepository::delete);
	}

	public RoomNumberResponse queryRoomNumber() {
    	String roomNumber = userRepository
				.findById(userFacade.getEmail())
				.orElseThrow(CredentialsNotFoundException::new)
				.getRoomNumber();
    	return new RoomNumberResponse(roomNumber);
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
