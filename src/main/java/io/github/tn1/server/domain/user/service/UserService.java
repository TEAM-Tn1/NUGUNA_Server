package io.github.tn1.server.domain.user.service;

import java.util.Optional;

import javax.transaction.Transactional;

import io.github.tn1.server.domain.user.presentation.dto.user.request.DeviceTokenRequest;
import io.github.tn1.server.domain.user.presentation.dto.user.request.InformationRequest;
import io.github.tn1.server.domain.user.presentation.dto.user.request.LoginRequest;
import io.github.tn1.server.domain.user.presentation.dto.user.request.RefreshTokenRequest;
import io.github.tn1.server.domain.user.presentation.dto.user.response.AccountResponse;
import io.github.tn1.server.domain.user.presentation.dto.user.response.OAuthLinkResponse;
import io.github.tn1.server.domain.user.presentation.dto.user.response.RoomNumberResponse;
import io.github.tn1.server.domain.user.presentation.dto.user.response.TokenResponse;
import io.github.tn1.server.domain.user.presentation.dto.user.response.UserInformationResponse;
import io.github.tn1.server.domain.refresh_token.RefreshToken;
import io.github.tn1.server.domain.refresh_token.RefreshTokenRepository;
import io.github.tn1.server.domain.user.domain.types.Role;
import io.github.tn1.server.domain.user.domain.User;
import io.github.tn1.server.domain.user.domain.repository.UserRepository;
import io.github.tn1.server.domain.user.exception.ExpiredRefreshTokenException;
import io.github.tn1.server.domain.user.exception.InvalidTokenException;
import io.github.tn1.server.domain.user.exception.UserNotFoundException;
import io.github.tn1.server.domain.user.facade.UserFacade;
import io.github.tn1.server.global.security.jwt.JwtTokenProvider;
import io.github.tn1.server.global.utils.api.client.DsmAuthClient;
import io.github.tn1.server.global.utils.api.dto.DsmAuthTokenRequest;
import io.github.tn1.server.global.utils.api.dto.InformationResponse;
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

    private static final String OAUTH_BASE_URI = "https://dsm-auth.vercel.app/";
    private static final HttpStatus DEFAULT_STATUS = HttpStatus.CREATED;
    private static final HttpStatus OK_STATUS = HttpStatus.OK;

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

        HttpStatus status = DEFAULT_STATUS;

        if(userRepository.findById(response.getEmail()).isPresent()) {
			User user = userRepository.save(
					userRepository.findById(response.getEmail())
							.orElseThrow(UserNotFoundException::new)
							.changeNameAndGcn(response.getName(), response.getGcn())
			);
            if(user.fillAllInformation())
            	status = OK_STATUS;
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

        return new ResponseEntity<>(
        		getToken(response.getEmail()), status);
    }

    public TokenResponse tokenRefresh(RefreshTokenRequest request) {
		if (!jwtTokenProvider.validateToken(request.getRefreshToken())){
			throw new InvalidTokenException();
		}
		return refreshTokenRepository.findByToken(request.getRefreshToken())
				.map(token -> getToken(token.getEmail()))
				.orElseThrow(ExpiredRefreshTokenException::new);
    }

    public UserInformationResponse queryUserInformation() {
    	return userFacade.getCurrentUser()
				.getInformation(userFacade.getCurrentEmail());
	}

	@Transactional
    public void modifyInformation(InformationRequest request) {
        userFacade.getCurrentUser()
				.writeInformation(request.getRoomNumber(),
						request.getAccountNumber());
    }

    public UserInformationResponse queryInformation(String email) {
        return userFacade.getCurrentUser()
                .getInformation(userFacade.getCurrentEmail());
    }

	public AccountResponse queryAccount() {
		return new AccountResponse(userRepository
				.findById(userFacade.getCurrentEmail())
				.map(User::getAccountNumber)
				.orElse(null));
	}

	@Transactional
	public void insertDeviceToken(DeviceTokenRequest request) {
		userFacade.getCurrentUser()
				.changeDeviceToken(request.getDeviceToken());
	}

	@Transactional
	public void logout() {
    	User user = userFacade.getCurrentUser();

		user.removeDeviceToken();

		refreshTokenRepository.findById(user.getEmail())
				.ifPresent(refreshTokenRepository::delete);
	}

	public RoomNumberResponse queryRoomNumber() {
    	String roomNumber = userFacade.getCurrentUser()
				.getRoomNumber();
    	return new RoomNumberResponse(roomNumber);
	}

	@Transactional
	public void ableAccountHide() {
    	userFacade.getCurrentUser()
				.ableHideAccount();
	}

	@Transactional
	public void disableAccountHide() {
		userFacade.getCurrentUser()
				.disableHideAccount();
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
