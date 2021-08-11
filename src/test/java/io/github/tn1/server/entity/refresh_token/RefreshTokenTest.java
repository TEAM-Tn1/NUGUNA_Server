package io.github.tn1.server.entity.refresh_token;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenTest {

    @Test
    @DisplayName("Builder를 활용하여 Question객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        String email = "test@gmail.com";
        String token = "asdfqwer1234.wqefxf8.123r8ds";
        Long ttl = 1234L;
        //when
        RefreshToken refreshToken = new RefreshToken(email, token, ttl);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(email, refreshToken.getEmail()),
                () -> Assertions.assertEquals(token, refreshToken.getToken()),
                () -> Assertions.assertEquals(ttl, refreshToken.getTtl())
        );
    }

}