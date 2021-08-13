package io.github.tn1.server.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InformationRequest {
    private String roomNumber;
    private String accountNumber;
}
