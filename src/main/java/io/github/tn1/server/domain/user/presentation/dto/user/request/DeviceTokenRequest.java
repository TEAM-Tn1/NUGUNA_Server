package io.github.tn1.server.domain.user.presentation.dto.user.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeviceTokenRequest {

	@NotNull(message = "device_token은 null이면 안됩니다.")
	@Size(max = 4096, message = "device_token은 4096자를 넘어서는 안됩니다.")
	private String deviceToken;

}
