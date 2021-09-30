package io.github.tn1.server.controller;

import javax.validation.Valid;

import io.github.tn1.server.dto.user.request.DeviceTokenRequest;
import io.github.tn1.server.dto.user.request.InformationRequest;
import io.github.tn1.server.dto.user.request.LoginRequest;
import io.github.tn1.server.dto.user.request.RefreshTokenRequest;
import io.github.tn1.server.dto.user.response.AccountResponse;
import io.github.tn1.server.dto.user.response.OAuthLinkResponse;
import io.github.tn1.server.dto.user.response.TokenResponse;
import io.github.tn1.server.dto.user.response.UserInformationResponse;
import io.github.tn1.server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/oauth")
    public OAuthLinkResponse getOAuthLink() {
        return userService.getOAuthLink();
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        return userService.login(request);
    }

    @PutMapping("/auth")
    public TokenResponse tokenRefresh(@RequestBody @Valid RefreshTokenRequest request) {
        return userService.tokenRefresh(request);
    }

    @PatchMapping("/information")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchInformation(@RequestBody InformationRequest request) {
        userService.modifyInformation(request);
    }

    @GetMapping("/{email}")
    public UserInformationResponse getInformation(@PathVariable("email") String email) {
        return userService.getInformation(email);
    }

    @GetMapping("/account")
	public AccountResponse getAccountNumber() {
    	return userService.getAccount();
	}

	@PostMapping("/device_token")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void insertDeviceToken(@RequestBody @Valid DeviceTokenRequest request) {
		userService.insertDeviceToken(request);
	}

	@PostMapping("/logout")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout() {
    	userService.logout();
	}

}
