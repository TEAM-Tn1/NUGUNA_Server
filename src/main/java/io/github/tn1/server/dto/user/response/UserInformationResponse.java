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
}
