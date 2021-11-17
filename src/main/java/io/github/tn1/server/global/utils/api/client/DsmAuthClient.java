package io.github.tn1.server.global.utils.api.client;

import io.github.tn1.server.global.utils.api.dto.DsmAuthTokenRequest;
import io.github.tn1.server.global.utils.api.dto.DsmAuthTokenResponse;
import io.github.tn1.server.global.utils.api.dto.InformationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "dsmAuthClient", url = "https://developer-api.dsmkr.com")
public interface DsmAuthClient {
    @PostMapping("/dsmauth/token")
    DsmAuthTokenResponse getToken(DsmAuthTokenRequest request);

    @GetMapping("/v1/info/basic/")
    InformationResponse getInformation(@RequestHeader("Authorization") String value);
}
