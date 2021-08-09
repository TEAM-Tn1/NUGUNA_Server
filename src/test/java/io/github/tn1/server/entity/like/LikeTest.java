package io.github.tn1.server.entity.like;

import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LikeTest {

    @Test
    @DisplayName("Builder를 활용하여 Like객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        Feed feed = Feed.builder()
                .title("피드")
                .build();
        User user = User.builder()
                .name("구매자")
                .build();
        //when
        Like like = Like.builder()
                .feed(feed)
                .user(user)
                .build();
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(feed, like.getFeed()),
                () -> Assertions.assertEquals(user, like.getUser())
        );
    }

}