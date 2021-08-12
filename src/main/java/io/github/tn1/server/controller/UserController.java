package io.github.tn1.server.controller;

import io.github.tn1.server.dto.user.request.LoginRequest;
import io.github.tn1.server.dto.user.request.RefreshTokenRequest;
import io.github.tn1.server.dto.user.response.OAuthLinkResponse;
import io.github.tn1.server.dto.user.response.TokenResponse;
import io.github.tn1.server.service.user.UserService;
import lombok.RequiredArgsConstructor;
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
    public TokenResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PutMapping("/auth")
    public TokenResponse tokenRefresh(@RequestBody RefreshTokenRequest request) {
        return userService.tokenRefresh(request);
    }

}
