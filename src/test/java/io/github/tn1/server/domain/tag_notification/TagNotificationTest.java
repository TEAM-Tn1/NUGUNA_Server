package io.github.tn1.server.domain.tag_notification;

import io.github.tn1.server.domain.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TagNotificationTest {

    @Test
    @DisplayName("Builder를 활용하여 User객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        String tag = "test Tag";
        User user = User.builder()
                .name("test")
                .build();
        //when
        TagNotification tagNotification = TagNotification.builder()
                .tag(tag)
                .user(user)
                .build();
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(tag, tagNotification.getTag()),
                () -> Assertions.assertEquals(user, tagNotification.getUser())
        );
    }

}