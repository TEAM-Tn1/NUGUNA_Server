package io.github.tn1.server.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInformationResponse {
    private String email;
    private String name;
    private String gcn;
    private String roomNumber;
    private String accountNumber;
}
