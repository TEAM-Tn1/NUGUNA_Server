package io.github.tn1.server.entity.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Builder를 활용하여 User객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        String email = "test@gmail.com";
        String accountNumber = "47------------KB";
        Role role = Role.ROLE_USER;

        // when
        User user = User.builder()
                .email(email)
                .role(role)
                .gcn("2114")
                .accountNumber(accountNumber)
                .name("이**")
                .roomNumber("318")
                .build();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(email, user.getEmail()),
                () -> Assertions.assertEquals(accountNumber, user.getAccountNumber()),
                () -> Assertions.assertEquals(role, user.getRole())
        );
    }

}