package io.github.tn1.server.dto.user.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

	@NotNull(message = "code는 null이면 안됩니다.")
    private String code;

}
