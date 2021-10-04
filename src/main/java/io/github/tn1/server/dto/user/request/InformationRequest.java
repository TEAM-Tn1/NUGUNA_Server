package io.github.tn1.server.dto.user.request;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InformationRequest {

	@Size(max = 3, message = "room_number는 3자를 넘어서는 안됩니다.")
    private String roomNumber;

	@Size(max = 20, message = "account_number는 20자를 넘어서는 안됩니다.")
    private String accountNumber;

}
