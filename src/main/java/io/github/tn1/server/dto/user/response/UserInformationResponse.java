package io.github.tn1.server.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInformationResponse {

    private final String email;
    private final String name;
    private final String gcn;
    private final String roomNumber;
    private final String accountNumber;
    private final Boolean hideAccount;

	public UserInformationResponse(String email, String name, String gcn, String roomNumber, String accountNumber) {
		this.email = email;
		this.name = name;
		this.gcn = gcn;
		this.roomNumber = roomNumber;
		this.accountNumber = accountNumber;
		this.hideAccount = null;
	}

}
